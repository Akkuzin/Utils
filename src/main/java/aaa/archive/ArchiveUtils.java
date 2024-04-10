package aaa.archive;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.getName;
import static org.apache.commons.lang3.StringUtils.containsAny;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.With;
import lombok.experimental.FieldDefaults;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

public class ArchiveUtils {

  @Builder
  @FieldDefaults(level = AccessLevel.PUBLIC)
  public static class FileData {
    @With byte[] data;
    @With String name;
  }

  public static Stream<FileData> listFiles(byte[] data, String name) {
    return Stream.of(FileData.builder().data(data).name(name).build())
        .flatMap(
            fileData -> {
              String extension = getExtension(fileData.name);
              if ("zip".equalsIgnoreCase(extension)) {
                return extractArchiveZip(fileData.data).stream();
              } else if ("7z".equalsIgnoreCase(extension)) {
                return extractArchive7Zip(fileData.data).stream();
              } else if ("rar".equalsIgnoreCase(extension)) {
                return extractArchiveRar(fileData.data).stream();
              } else {
                return Stream.of(fileData);
              }
            });
  }

  @SneakyThrows
  public static List<FileData> extractArchiveZip(byte[] data) {
    charset:
    for (Charset charset :
        asList(Charset.forName("CP1251"), StandardCharsets.UTF_8, Charset.forName("CP866"))) {
      try {
        List<FileData> result = new ArrayList<>();
        try (ByteArrayInputStream is = new ByteArrayInputStream(data);
            ZipInputStream zis = new ZipInputStream(is, charset)) {
          ZipEntry ze;
          while ((ze = zis.getNextEntry()) != null) {
            String name = getName(ze.getName());
            if (containsAny(name, "®©‘’ЌЎ¦Ґўћџ®«¬")) {
              continue charset;
            }
            result.add(FileData.builder().data(IOUtils.toByteArray(zis)).name(name).build());
          }
        }
        return result;
      } catch (IllegalArgumentException e) {
        if (!"MALFORMED".equals(e.getMessage())) {
          throw e;
        }
      }
    }
    return emptyList();
  }

  @SneakyThrows
  public static List<FileData> extractArchive7Zip(byte[] data) {
    List<FileData> result = new ArrayList<>();
    try (SeekableInMemoryByteChannel byteChannel = new SeekableInMemoryByteChannel(data);
        SevenZFile sevenZFile = SevenZFile.builder().setSeekableByteChannel(byteChannel).get()) {
      SevenZArchiveEntry entry;
      while ((entry = sevenZFile.getNextEntry()) != null) {
        result.add(
            FileData.builder()
                .data(IOUtils.toByteArray(new SevenZipInputStream(sevenZFile)))
                .name(getName(entry.getName()))
                .build());
      }
    }
    return result;
  }

  @AllArgsConstructor
  public static class SevenZipInputStream extends InputStream {

    SevenZFile sevenZFile;

    @Override
    public int read() throws IOException {
      return sevenZFile.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
      return sevenZFile.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      return sevenZFile.read(b, off, len);
    }
  }

  @SneakyThrows
  public static List<FileData> extractArchiveRar(byte[] data) {
    List<FileData> result = new ArrayList<>();
    Archive archive = new Archive(new ByteArrayInputStream(data));
    FileHeader fileHeader;
    while ((fileHeader = archive.nextFileHeader()) != null) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      archive.extractFile(fileHeader, bos);
      result.add(FileData.builder().data(bos.toByteArray()).name(fileHeader.getFileName()).build());
    }
    return result;
  }

  @SneakyThrows
  public static List<ArchiveUtils.FileData> extractArchiveArj(byte[] data) {
    List<ArchiveUtils.FileData> result = new ArrayList<>();
    try (ArchiveInputStream arjInput =
        new ArchiveStreamFactory(StandardCharsets.UTF_8.name())
            .createArchiveInputStream(new ByteArrayInputStream(data))) {
      ArchiveEntry entry;
      while ((entry = arjInput.getNextEntry()) != null) {
        if (!entry.isDirectory()) {
          result.add(
              FileData.builder().name(entry.getName()).data(IOUtils.toByteArray(arjInput)).build());
        }
      }
    }
    return result;
  }

  @SneakyThrows
  public static void addFile(ZipOutputStream zipOutStream, byte[] data, String name) {
    if (data != null) {
      addFile(zipOutStream, new ByteArrayInputStream(data), name);
    }
  }

  @SneakyThrows
  public static void addFile(ZipOutputStream zipOutStream, InputStream data, String name) {
    if (data != null) {
      zipOutStream.putNextEntry(new ZipEntry(name));
      IOUtils.copy(data, zipOutStream);
      zipOutStream.closeEntry();
    }
  }

  @SneakyThrows
  public static byte[] extractGzip(byte[] data) {
    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (GzipCompressorInputStream in =
        new GzipCompressorInputStream(new ByteArrayInputStream(data))) {
      IOUtils.copy(in, out);
    }
    return out.toByteArray();
  }

  public static byte[] compressGzip(byte[] data) {
    return compressGzip(data, GZIP_BEST);
  }

  @SneakyThrows
  public static byte[] compressGzip(byte[] data, GzipParameters parameters) {
    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (BufferedOutputStream bos = new BufferedOutputStream(out);
        GzipCompressorOutputStream output = new GzipCompressorOutputStream(bos, parameters)) {
      IOUtils.copy(new ByteArrayInputStream(data), output);
    }
    return out.toByteArray();
  }

  public static final GzipParameters GZIP_BEST = makeGzipBestParameters();

  static GzipParameters makeGzipBestParameters() {
    GzipParameters params = new GzipParameters();
    params.setCompressionLevel(Deflater.BEST_COMPRESSION);
    return params;
  }

  @SneakyThrows
  public static byte[] extractXz(byte[] data) {
    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (CompressorInputStream in = new XZCompressorInputStream(new ByteArrayInputStream(data))) {
      IOUtils.copy(in, out);
    }
    return out.toByteArray();
  }

  public static byte[] compressXz(byte[] data) {
    return compressXz(data, 9);
  }

  @SneakyThrows
  public static byte[] compressXz(byte[] data, int preset) {
    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (BufferedOutputStream bos = new BufferedOutputStream(out);
        XZCompressorOutputStream output = new XZCompressorOutputStream(bos, preset)) {
      IOUtils.copy(new ByteArrayInputStream(data), output);
    }
    return out.toByteArray();
  }

  @SneakyThrows
  public static byte[] extractLz4(byte[] data) {

    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (CompressorInputStream in =
        new FramedLZ4CompressorInputStream(new ByteArrayInputStream(data))) {
      IOUtils.copy(in, out);
    }
    return out.toByteArray();
  }

  public static byte[] compressLz4(byte[] data) {
    return compressLz4(data, FramedLZ4CompressorOutputStream.Parameters.DEFAULT);
  }

  @SneakyThrows
  public static byte[] compressLz4(
      byte[] data, FramedLZ4CompressorOutputStream.Parameters parameters) {
    if (ArrayUtils.isEmpty(data)) {
      return data;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (BufferedOutputStream bos = new BufferedOutputStream(out);
        CompressorOutputStream output = new FramedLZ4CompressorOutputStream(bos, parameters)) {
      IOUtils.copy(new ByteArrayInputStream(data), output);
    }
    return out.toByteArray();
  }
}
