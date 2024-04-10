package aaa.language.ru.number;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import aaa.dictionary.si.NumberPrefix;
import aaa.language.ru.Gender;
import com.google.common.collect.ImmutableMap;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtils {

  @SuppressWarnings("nls")
  private static final Map<NumberPrefix, PluralVariants> PREFIX_NAME =
      ImmutableMap.<NumberPrefix, PluralVariants>builder()
          .put(NumberPrefix.k, PluralVariants.by("тысяча", "тысячи", "тысяч"))
          .put(NumberPrefix.M, PluralVariants.by("миллион", "миллиона", "миллионов"))
          .put(NumberPrefix.G, PluralVariants.by("миллиард", "миллиарда", "миллиардов"))
          .put(NumberPrefix.T, PluralVariants.by("триллион", "триллиона", "триллионов"))
          .put(NumberPrefix.P, PluralVariants.by("квадриллион", "квадриллиона", "кваддриллионов"))
          .put(NumberPrefix.E, PluralVariants.by("квинтиллион", "квинтиллиона", "квинтиллионов"))
          .put(NumberPrefix.Z, PluralVariants.by("секстиллион", "секстиллиона", "секстиллионов"))
          .put(NumberPrefix.Y, PluralVariants.by("септиллион", "септиллиона", "септиллионов"))
          .build();

  public static String getSiPrefixName(NumberPrefix prefix, int value) {
    return PREFIX_NAME.get(prefix).resolve(value);
  }

  private static final Map<NumberPrefix, Gender> PREFIX_GENDER =
      ImmutableMap.<NumberPrefix, Gender>builder()
          .put(NumberPrefix.k, Gender.FEMALE)
          .put(NumberPrefix.M, Gender.MALE)
          .put(NumberPrefix.G, Gender.MALE)
          .put(NumberPrefix.T, Gender.MALE)
          .put(NumberPrefix.P, Gender.MALE)
          .put(NumberPrefix.E, Gender.MALE)
          .put(NumberPrefix.Z, Gender.MALE)
          .put(NumberPrefix.Y, Gender.MALE)
          .build();

  public static Gender getSiPrefixGender(NumberPrefix prefix) {
    return PREFIX_GENDER.get(prefix);
  }

  /** Получение названия числа (от 1 до 999) прописью */
  @SuppressWarnings("nls")
  private static String number3SymbolsToWords(int number, Gender gender) {
    List<String> result = new ArrayList<>(3);
    int num = number;
    if (num >= 100) {
      switch (num / 100) {
        case 1 -> result.add("сто");
        case 2 -> result.add("двести");
        case 3 -> result.add("триста");
        case 4 -> result.add("четыреста");
        case 5 -> result.add("пятьсот");
        case 6 -> result.add("шестьсот");
        case 7 -> result.add("семьсот");
        case 8 -> result.add("восемьсот");
        case 9 -> result.add("девятьсот");
      }
      num %= 100;
    }

    if (num > 9) {
      switch (num / 10) {
        case 1 -> {
          switch (num % 10) {
            case 0 -> result.add("десять");
            case 1 -> result.add("одиннадцать");
            case 2 -> result.add("двенадцать");
            case 3 -> result.add("тринадцать");
            case 4 -> result.add("четырнадцать");
            case 5 -> result.add("пятнадцать");
            case 6 -> result.add("шестнадцать");
            case 7 -> result.add("семнадцать");
            case 8 -> result.add("восемнадцать");
            case 9 -> result.add("девятнадцать");
          }
          num = 0;
        }
        case 2 -> result.add("двадцать");
        case 3 -> result.add("тридцать");
        case 4 -> result.add("сорок");
        case 5 -> result.add("пятьдесят");
        case 6 -> result.add("шестьдесят");
        case 7 -> result.add("семьдесят");
        case 8 -> result.add("восемьдесят");
        case 9 -> result.add("девяносто");
      }
      num %= 10;
    }

    if (num > 0) {
      switch (num) {
        case 1 -> {
          switch (gender) {
            case MALE -> result.add("один");
            case FEMALE -> result.add("одна");
            case NEUTER -> result.add("одно");
          }
        }
        case 2 -> {
          switch (gender) {
            case FEMALE -> result.add("две");
            case MALE, NEUTER -> result.add("два");
          }
        }
        case 3 -> result.add("три");
        case 4 -> result.add("четыре");
        case 5 -> result.add("пять");
        case 6 -> result.add("шесть");
        case 7 -> result.add("семь");
        case 8 -> result.add("восемь");
        case 9 -> result.add("девять");
      }
    }
    return join(result);
  }

  public static String integerToWords(long number, Gender gender) {
    return integerToWords(BigInteger.valueOf(number), gender);
  }

  @SuppressWarnings("nls")
  public static String integerToWords(BigInteger number, Gender gender) {
    if (number == null) {
      return null;
    }
    List<String> result = new ArrayList<>();

    switch (number.signum()) {
      case 0:
        result.add("ноль");
        break;
      case -1:
        result.add("минус");
        // $FALL-THROUGH$
      case 1:
        BigInteger[] Ymod = number.divideAndRemainder(NumberPrefix.Y.getValueInteger());
        if (!BigInteger.ZERO.equals(Ymod[0])) {
          result.add(integerToWords(Ymod[0], PREFIX_GENDER.get(NumberPrefix.Y)));
          result.add(PREFIX_NAME.get(NumberPrefix.Y).resolve(Ymod[0].mod(BigInteger.TEN)));
        }
        BigInteger num = Ymod[1];
        for (NumberPrefix prefix :
            asList(
                NumberPrefix.Z,
                NumberPrefix.E,
                NumberPrefix.P,
                NumberPrefix.T,
                NumberPrefix.G,
                NumberPrefix.M,
                NumberPrefix.k)) {
          BigInteger[] mod = num.divideAndRemainder(prefix.getValueInteger());
          if (!BigInteger.ZERO.equals(mod[0])) {
            result.add(number3SymbolsToWords(mod[0].intValue(), PREFIX_GENDER.get(prefix)));
            result.add(PREFIX_NAME.get(prefix).resolve(mod[0].mod(BigInteger.TEN)));
          }
          num = mod[1];
        }
        result.add(number3SymbolsToWords(num.intValue(), gender));
    }

    return join(result);
  }

  public static String fractionToWords(double value, Gender gender) {
    return fractionToWords(BigDecimal.valueOf(value), gender);
  }

  @SuppressWarnings("nls")
  public static String fractionToWords(BigDecimal number, Gender gender) {
    if (number == null) {
      return null;
    }
    List<String> result = new ArrayList<>();
    BigDecimal fraction = number.abs();
    BigDecimal frac = fraction.subtract(fraction.setScale(0, RoundingMode.FLOOR));
    frac =
        frac.scaleByPowerOfTen(NumberPrefix.Y.getPower())
            .setScale(0, RoundingMode.FLOOR)
            .stripTrailingZeros();
    int len = NumberPrefix.Y.getPower() + frac.scale();
    if (frac.precision() > 0) {
      result.add(integerToWords(frac.unscaledValue(), gender));
      // Формирование знаменателя
      String pref = "", root = "";
      root =
          switch ((len - 1) / 3) {
            case 0 ->
                switch (len) {
                  case 1 -> "десят";
                  case 2 -> "сот";
                  case 3 -> "тысячн";
                  default -> root;
                };
            default -> {
              pref =
                  switch (len % 3) {
                    case 1 -> "десяти";
                    case 2 -> "сто";
                    default -> "";
                  };
              yield switch ((len - 1) / 3) {
                case 1 -> "тысячн";
                case 2 -> "миллионн";
                case 3 -> "миллиардн";
                case 4 -> "триллионн";
                case 5 -> "квадрилионн";
                case 6 -> "квинтиллионн";
                case 7 -> "секстиллионн";
                default -> "септилионн";
              };
            }
          };
      String suff =
          PluralVariants.by(
                  switch (gender) {
                    case MALE -> "ый";
                    case FEMALE -> "ая";
                    case NEUTER -> "ое";
                  },
                  "ых")
              .resolve(frac.unscaledValue().mod(BigInteger.TEN.pow(2)));
      result.add(pref + root + suff);
    }
    return join(result);
  }

  @SuppressWarnings("nls")
  public static String numberToWords(BigDecimal number, Gender gender) {
    List<String> result = new ArrayList<>(3);
    BigDecimal absoluteNumber = number.abs();
    BigInteger trunc = number.toBigInteger();
    if (number.stripTrailingZeros().scale() > 0) {
      // добавление целой части
      result.add(integerToWords(trunc, gender));
      result.add(
          PluralVariants.by(
                  switch (gender) {
                    case MALE -> "целый";
                    case FEMALE -> "целая";
                    case NEUTER -> "целое";
                  },
                  "целых")
              .resolve(trunc));
      // добавление дробной части
      result.add(fractionToWords(absoluteNumber, gender));
    } else {
      result.add(integerToWords(trunc, gender));
    }
    return join(result);
  }

  private static String join(Collection<String> parts) {
    return parts.stream().filter(StringUtils::isNotBlank).collect(joining(" "));
  }
}
