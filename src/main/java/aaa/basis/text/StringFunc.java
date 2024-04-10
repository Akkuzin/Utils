package aaa.basis.text;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class StringFunc {

  /** Concatenate strings if both is not empty/null */
  public static String concatWithDelim(String str1, String delimiter, String str2) {
    return Stream.of(str1, str2).filter(StringUtils::isNotBlank).collect(joining(delimiter));
  }

  /**
   * Gets the substring after the last occurrence of a separator. The separator is not returned. If
   * there is no separator found then whole string is returned.
   *
   * <p>A <code>null</code> string input will return <code>null</code>. An empty ("") string input
   * will return the empty string. An empty or <code>null</code> separator will return the empty
   * string if the input string is not <code>null</code>.
   *
   * <pre>
   * StringUtils.substringAfterLast(null, *)      = null
   * StringUtils.substringAfterLast("", *)        = ""
   * StringUtils.substringAfterLast(*, "")        = ""
   * StringUtils.substringAfterLast(*, null)      = ""
   * StringUtils.substringAfterLast("abc", "a")   = "bc"
   * StringUtils.substringAfterLast("abcba", "b") = "a"
   * StringUtils.substringAfterLast("abc", "c")   = ""
   * StringUtils.substringAfterLast("a", "a")     = ""
   * StringUtils.substringAfterLast("a", "z")     = "a"
   * </pre>
   *
   * @param str the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring after the last occurrence of the separator, <code>null</code> if null
   *     String input
   */
  public static String substringAfterLastIfExists(String str, String separator) {
    if (isEmpty(str)) {
      return str;
    }
    if (isEmpty(separator)) {
      return EMPTY;
    }
    int pos = str.lastIndexOf(separator);
    if (pos == -1) {
      return str;
    }
    if (pos == str.length() - separator.length()) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  public static String ellipsis(String value, int maxLength) {
    return value == null
        ? null
        : value.length() > maxLength
            ? maxLength == 0 ? "" : value.substring(0, Math.max(maxLength - 1, 0)) + "…"
            : value;
  }
}
