package aaa.currency;

import static aaa.currency.CurrencyUtils.decimalPlacesAfterComma;
import static aaa.currency.CurrencyUtils.decimalPlacesBeforeComma;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CurrencyUtilsTest {

  @Test
  public void testCountDigitsBeforeComma() {
    assertEquals(3, decimalPlacesBeforeComma(BigDecimal.valueOf(123.123D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.valueOf(0.123D)));
    assertEquals(3, decimalPlacesBeforeComma(BigDecimal.valueOf(999.999D)));
    assertEquals(2, decimalPlacesBeforeComma(BigDecimal.valueOf(99.0D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.valueOf(0.0D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.ZERO));
    assertEquals(3, decimalPlacesBeforeComma(BigDecimal.valueOf(-123.123D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.valueOf(-0.123D)));
    assertEquals(3, decimalPlacesBeforeComma(BigDecimal.valueOf(-999.999D)));
    assertEquals(2, decimalPlacesBeforeComma(BigDecimal.valueOf(-99.0D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.valueOf(-0.0D)));
    assertEquals(1, decimalPlacesBeforeComma(BigDecimal.ZERO.negate()));
  }

  @Test
  public void testCountDigitsAfterComma() {
    assertEquals(0, decimalPlacesAfterComma(BigDecimal.ZERO));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("123.45678")));
    assertEquals(2, decimalPlacesAfterComma(new BigDecimal("123.45000")));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("00123.45678")));
    assertEquals(3, decimalPlacesAfterComma(new BigDecimal("00123.45600")));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("1234.5678").scaleByPowerOfTen(-1)));
    assertEquals(3, decimalPlacesAfterComma(new BigDecimal("1234.5678").scaleByPowerOfTen(1)));
    assertEquals(0, decimalPlacesAfterComma(BigDecimal.ZERO.negate()));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("-123.45678")));
    assertEquals(2, decimalPlacesAfterComma(new BigDecimal("-123.45000")));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("-00123.45678")));
    assertEquals(3, decimalPlacesAfterComma(new BigDecimal("-00123.45600")));
    assertEquals(5, decimalPlacesAfterComma(new BigDecimal("-1234.5678").scaleByPowerOfTen(-1)));
    assertEquals(3, decimalPlacesAfterComma(new BigDecimal("-1234.5678").scaleByPowerOfTen(1)));
  }
}
