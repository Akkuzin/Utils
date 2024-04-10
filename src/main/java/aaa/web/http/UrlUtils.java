package aaa.web.http;

import aaa.nvl.Nvl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class UrlUtils {

  public static final String DEFAULT_ENCODING = "UTF-8";
  private static final String SHORT_SPACE_URL_REPLACEMENT = "+";
  private static final String LONG_SPACE_URL_REPLACEMENT = "%20";

  public static String prepareString(String value, String encoding, Integer maxLength)
      throws UnsupportedEncodingException {
    String reducedLength =
        StringUtils.substring(
            StringUtils.stripToEmpty(value), 0, Nvl.nvl(maxLength, Integer.MAX_VALUE));
    return StringUtils.replace(
        URLEncoder.encode(reducedLength, encoding),
        SHORT_SPACE_URL_REPLACEMENT,
        LONG_SPACE_URL_REPLACEMENT);
  }

  @SneakyThrows
  public static String urlEncodeUtf8(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }
}
