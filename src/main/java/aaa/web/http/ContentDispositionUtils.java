package aaa.web.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang3.StringUtils.contains;

public class ContentDispositionUtils {

	static final String HEADER_PARAMETER_ATTACHMENT = "attachment; "; //$NON-NLS-1$
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
		// To inspect details for the below code, see http://greenbytes.de/tech/tc2231/

		String preparedName = UrlUtils.prepareString(filename, FILENAME_ENCODING, null);
		if (contains(userAgent, MSIE) || contains(userAgent, CHROME)) {
			// IE does not support internationalized filename at all.
			// It can only recognize internationalized URL, so we do the trick via routing rules.
			filenameHeader = String.format(IE_TEMPLATE, preparedName);
			//		} else if (contains(ag, "webkit")) {
			//			// Safari 3.0 and Chrome 2.0 accepts UTF-8 encoded string directly.
			//			filenameHeader = "filename=" + filename;
		} else {
			// For others like Firefox, we follow RFC2231 (encoding extension in HTTP headers).
			filenameHeader = String.format(FIREFOX_TEMPLATE, preparedName);
		}
		return filenameHeader;
	}

}
