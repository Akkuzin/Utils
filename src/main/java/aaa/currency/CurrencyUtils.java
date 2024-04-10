package aaa.currency;

import static aaa.nvl.Nvl.nvl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyUtils {

  public static final int DEFAULT_DIGITS_AFTER_COMMA = 2;

  public static BigDecimal round(BigDecimal value, Integer digitsNumAfterComma) {
    if (value == null) {
      return null;
    }
    return value.setScale(
        nvl(digitsNumAfterComma, DEFAULT_DIGITS_AFTER_COMMA), RoundingMode.HALF_UP);
  }

  public static BigDecimal round(BigDecimal value) {
    return round(value, null);
  }

  public static int decimalPlacesBeforeComma(BigDecimal value) {
    return value.abs().setScale(0, RoundingMode.FLOOR).precision();
  }

  public static int decimalPlacesAfterComma(BigDecimal value) {
    value = value.abs();
    value = value.subtract(value.setScale(0, RoundingMode.FLOOR)).stripTrailingZeros();
    return value.signum() == 0 ? 0 : value.precision();
  }
}
