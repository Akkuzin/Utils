package aaa.i18n.ru;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.replaceChars;

import com.google.common.collect.Sets;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class TransliterateUtils {

  public static final String WHOLE_LATIN_KEYBOARD =
      "QWERTYUIOP[]ASDFGHJKL:'ZXCVBNM<> qwertyuiop[]asdfghjkl;'zxcvbnm,.";
  public static final String WHOLE_RUSSIAN_KEYBOARD =
      "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ йцукенгшщзхъфывапролджэячсмитьбю";

  /** Исправление неправильной раскладки при наборе текста */
  public static Stream<String> variateFix(String value) {
    return Stream.of(value, fixLayout(value), fixLayoutRev(value))
        .filter(StringUtils::isNotBlank)
        .distinct();
  }

  /** Исправление неправильной раскладки при наборе текста */
  public static String fixLayout(String value) {
    return replaceChars(defaultIfEmpty(value, ""), WHOLE_LATIN_KEYBOARD, WHOLE_RUSSIAN_KEYBOARD);
  }

  /** Исправление неправильной раскладки при наборе текста */
  public static String fixLayoutRev(String value) {
    return replaceChars(defaultIfEmpty(value, ""), WHOLE_RUSSIAN_KEYBOARD, WHOLE_LATIN_KEYBOARD);
  }

  public static Stream<String> variateMix(String value) {
    return (!isMix(value)
            ? Stream.of(value)
            : Stream.of(prepareEngCode(value), prepareRusCode(value), value))
        .filter(StringUtils::isNotBlank)
        .distinct();
  }

  public static final Pattern LATIN = Pattern.compile("\\p{IsLatin}+");
  public static final Pattern RUSSIAN = Pattern.compile("\\p{IsCyrillic}+");

  public static boolean isRus(String value) {
    return value != null && RUSSIAN.matcher(value).find();
  }

  public static boolean isLat(String value) {
    return value != null && LATIN.matcher(value).find();
  }

  public static boolean isMix(String value) {
    return isLat(value) && isRus(value);
  }

  public static final String MIX_LATIN = "YOMEPKABCTXyomepkabctx";
  public static final String MIX_RUSSIAN = "УОМЕРКАВСТХуомеркавстх";

  /** Исправление случайно заменённых букв на аналогичные по написанию из другого алфавита */
  public static String prepareRusCode(String code) {
    return replaceChars(code, MIX_LATIN, MIX_RUSSIAN);
  }

  /** Исправление случайно заменённых букв на аналогичные по написанию из другого алфавита */
  public static String prepareEngCode(String code) {
    return replaceChars(code, MIX_RUSSIAN, MIX_LATIN);
  }

  public static boolean eqVariate(Function<String, Stream<String>> variator, String... values) {
    return Stream.of(values)
        .map(variator)
        .map(s -> s.collect(toSet()))
        .reduce(Sets::intersection)
        .map(s -> !s.isEmpty())
        .orElse(false);
  }
}
