/** */
package aaa.type.interval;

import aaa.format.SafeParse;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntervalUtils {

  public static Long parseIntervalSafe(String value) {
    try {
      return parseInterval(value);
    } catch (ParseException e) {
      return null;
    }
  }

  public static final String INTERVAL_EXPRESSION =
      "\\s*(\\d*?)(\\s*(\\d+)\\s*:\\s*(\\d\\d)\\s*(:\\s*(\\d\\d)\\s*(:\\s*(\\d{0,3}))?)?)?\\s*";
  public static final Pattern INTERVAL_PATTERN = Pattern.compile(INTERVAL_EXPRESSION);

  private static final int DAYS_GROUP_NUM = 1;
  private static final int HOURS_GROUP_NUM = 3;
  private static final int MINUTES_GROUP_NUM = 4;
  private static final int SECONDS_GROUP_NUM = 6;
  private static final int MILLIS_GROUP_NUM = 8;

  private static final int NO_LIMIT = 0;

  private static Long getLong(Matcher matcher, int group, int max) throws ParseException {
    String value = matcher.group(group);
    Long result = SafeParse.parseLong(value, 0L);
    if (max != NO_LIMIT && result >= max) {
      throw new ParseException(value, matcher.start(group));
    }
    return result;
  }

  /**
   * Возвращает численное значение интервала в миллисекундах
   *
   * @param value Строковое представление интервала
   * @return Interval in milliseconds
   * @throws ParseException
   */
  public static long parseInterval(String value) throws ParseException {
    Matcher matcher = INTERVAL_PATTERN.matcher(value);
    if (!matcher.matches()) {
      throw new ParseException("Unable to parse interval", 0);
    }
    long result = 0L;
    result += getLong(matcher, DAYS_GROUP_NUM, NO_LIMIT);
    result *= HOURS_PER_DAY;
    result += getLong(matcher, HOURS_GROUP_NUM, result != 0 ? HOURS_PER_DAY : NO_LIMIT);
    result *= MINUTES_PER_HOUR;
    result += getLong(matcher, MINUTES_GROUP_NUM, MINUTES_PER_HOUR);
    result *= SECONDS_PER_MINUTE;
    result += getLong(matcher, SECONDS_GROUP_NUM, SECONDS_PER_MINUTE);
    result *= MILLIS_PER_SECOND;
    result += getLong(matcher, MILLIS_GROUP_NUM, MILLIS_PER_SECOND);
    return result;
  }

  private static final int HOURS_PER_DAY = 24;
  private static final int MINUTES_PER_HOUR = 60;
  private static final int SECONDS_PER_MINUTE = 60;
  private static final int MILLIS_PER_SECOND = 1000;
}
