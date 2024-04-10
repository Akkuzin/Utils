package aaa.web.http;

import static org.apache.commons.lang3.StringUtils.contains;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ContentDispositionUtils {

  static final String HEADER_PARAMETER_ATTACHMENT = "attachment; ";
  static final String FILENAME = "filename";
  static final String FILENAME_ENCODING = StandardCharsets.UTF_8.name();
  static final String CHROME = "chrome";
  static final String MSIE = "msie";
  static final String USER_AGENT = "User-Agent";
  static final String CONTENT_DISPOSITION = "Content-Disposition";

  static final String IE_TEMPLATE = FILENAME + "=\"%s\"";
  static final String FIREFOX_TEMPLATE = FILENAME + "*=" + FILENAME_ENCODING + "''%s";

  protected static String makeContentDisposition(String filename, String userAgent)
      throws UnsupportedEncodingException {
    String filenameHeader;
    String preparedName = UrlUtils.prepareString(filename, FILENAME_ENCODING, null);
    if (contains(userAgent, MSIE) || contains(userAgent, CHROME)) {
      filenameHeader = String.format(IE_TEMPLATE, preparedName);
    } else {
      // For others like Firefox, we follow RFC2231 (encoding extension in HTTP headers).
      filenameHeader = String.format(FIREFOX_TEMPLATE, preparedName);
    }
    return filenameHeader;
  }
}
