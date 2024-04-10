/** */
package aaa.nvl;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Nvl {

  /** NVL family. Protect variable against null value */
  public static <T> T nvl(T value, T valueDefault) {
    return value != null ? value : valueDefault;
  }

  /** NVL family. Protect variables against null value */
  public static <T> T coalesce(T value1, T value2) {
    return nvl(value1, value2);
  }

  public static <T> T coalesce(Collection<T> values) {
    return values.stream().filter(Objects::nonNull).findFirst().orElse(null);
  }

  public static <T> T coalesce(T... values) {
    return coalesce(asList(values));
  }

  public static <T> Optional<T> coalesce(Supplier<Optional<T>>... producers) {
    return Stream.of(producers)
        .filter(Objects::nonNull)
        .map(Supplier::get)
        .filter(Optional::isPresent)
        .findFirst()
        .orElse(Optional.empty());
  }

  /** NVL2 family. Protect variable against null value */
  public static <T> T nvl2(Object value1, T value2, T value3) {
    return value1 != null ? value2 : value3;
  }

  /** Null safe toString translation */
  public static String nvlToString(Object value, String valueDefault) {
    return value != null ? value.toString() : valueDefault;
  }

  /** Null safe toString translation */
  public static String nvlToString(Object value) {
    return nvlToString(value, EMPTY);
  }

  /** Null safe formatting */
  public static String nvlFormat(Object value, Format format, String valueDefault) {
    return value != null && format != null ? format.format(value) : valueDefault;
  }

  /** Null safe formatting */
  public static Format nvlFormat(Format format, String valueDefault) {
    return new Format() {
      @Override
      public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (format == null || obj == null) {
          toAppendTo.append(valueDefault);
        } else {
          format.format(obj, toAppendTo, pos);
        }
        return toAppendTo;
      }

      @Override
      public Object parseObject(String source, ParsePosition pos) {
        return null;
      }
    };
  }

  public static <T extends Comparable<T>> T max(T... values) {
    return max(asList(values));
  }

  /** Compares comparable values returning greater one */
  public static <T extends Comparable<T>> T max(Collection<? extends T> values) {
    return values.stream().filter(Objects::nonNull).max(Comparable::compareTo).orElse(null);
  }

  public static <T extends Comparable<T>> T min(T... values) {
    return min(asList(values));
  }

  /** Compares two comparable values returning lesser */
  public static <T extends Comparable<T>> T min(Collection<? extends T> values) {
    return values.stream().filter(Objects::nonNull).min(Comparable::compareTo).orElse(null);
  }

  public static <T> T nvlGet(List<T> list, int index) {
    return list != null && index >= 0 && index < list.size() ? list.get(index) : null;
  }

  public static <T> T nvlGet(T[] list, int index) {
    return list != null && index >= 0 && index < list.length ? list[index] : null;
  }

  public static <T> T nvlGetFirst(List<T> list) {
    return nvlGet(list, 0);
  }

  public static <T> T nvlGetFirst(T[] list) {
    return nvlGet(list, 0);
  }
}
