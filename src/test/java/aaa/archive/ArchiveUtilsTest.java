package aaa.archive;

import static aaa.archive.ArchiveUtils.compressGzip;
import static aaa.archive.ArchiveUtils.compressLz4;
import static aaa.archive.ArchiveUtils.compressXz;
import static aaa.archive.ArchiveUtils.extractGzip;
import static aaa.archive.ArchiveUtils.extractLz4;
import static aaa.archive.ArchiveUtils.extractXz;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

class ArchiveUtilsTest {

  int size = 10 * 1024 * 1024;
  byte[] testData = new byte[size];

  {
    new Random(123L).nextBytes(testData);
  }

  @Test
  public void gzipTest() {
    assertThat(extractGzip(compressGzip(testData))).isEqualTo(testData);
  }

  @Test
  public void sevenXzTest() {
    assertThat(extractXz(compressXz(testData))).isEqualTo(testData);
  }

  @Test
  public void sevenLz4Test() {
    assertThat(extractLz4(compressLz4(testData))).isEqualTo(testData);
  }
}
