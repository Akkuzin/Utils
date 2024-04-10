package aaa.type.interval;

import static aaa.type.interval.IntervalUtils.parseInterval;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class IntervalUtilsTest {

  long millis(long days, int hours, int minutes, int seconds) {
    return (((days * 24 + hours) * 60 + minutes) * 60 + seconds) * 1000L;
  }

  @Test
  @SneakyThrows
  public void testPositive() {
    assertEquals(millis(111, 0, 0, 0), parseInterval("111"));
    assertEquals(millis(0, 0, 0, 30), parseInterval("00:00:30"));
    assertEquals(millis(0, 111, 12, 0), parseInterval("111:12"));
    assertEquals(millis(0, 11, 12, 0), parseInterval("11:12"));
    assertEquals(millis(111, 12, 13, 0), parseInterval("111 12:13"));
    assertEquals(millis(11123456789L, 0, 0, 0), parseInterval("11123456789"));
  }

  @Test
  @SneakyThrows
  public void testPositiveWithSpaces() {
    assertEquals(millis(111, 0, 0, 0), parseInterval("    111    "));
    assertEquals(millis(0, 111, 12, 0), parseInterval(" 111 \t : 12  "));
    assertEquals(millis(0, 11, 12, 0), parseInterval("	11  :  12\n   "));
    assertEquals(millis(111, 12, 13, 0), parseInterval("111 12:13"));
    assertEquals(millis(111, 0, 0, 0), parseInterval("	\n111 	"));
  }

  @Test
  public void testNegative1() {
    assertThatThrownBy(() -> parseInterval("11:123")).isInstanceOf(ParseException.class);
  }

  @Test
  public void testNegative2() {
    assertThatThrownBy(() -> parseInterval("XXX")).isInstanceOf(ParseException.class);
  }

  @Test
  public void testNegative3() {
    assertThatThrownBy(() -> parseInterval("11:123 19")).isInstanceOf(ParseException.class);
  }

  @Test
  public void testNegative4() {
    assertThatThrownBy(() -> parseInterval("11::12")).isInstanceOf(ParseException.class);
  }
}
