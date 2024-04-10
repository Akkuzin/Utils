package aaa.language.ru.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import aaa.language.ru.Gender;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
public class NumberUtilsTest {

  @Test
  public void getPluralNumberEnding() {
    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(0));
    assertEquals("топор", PluralVariants.by("топор", "топора", "топоров").resolve(1));
    assertEquals("гора", PluralVariants.by("гора", "горы", "гор").resolve(1));
    assertEquals("облака", PluralVariants.by("облако", "облака", "облаков").resolve(2));
    assertEquals("ножниц", PluralVariants.by("ножницы", "ножниц", "ножниц").resolve(5));
    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(6));

    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(111));
    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(214));
    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(315));

    assertEquals("топор", PluralVariants.by("топор", "топора", "топоров").resolve(131));
    assertEquals("топора", PluralVariants.by("топор", "топора", "топоров").resolve(244));
    assertEquals("топоров", PluralVariants.by("топор", "топора", "топоров").resolve(355));
  }

  @Test
  public void integerToWordsZeroTest() {
    assertEquals("ноль", NumberUtils.integerToWords(BigInteger.ZERO, Gender.MALE));
    assertEquals("ноль", NumberUtils.integerToWords(BigInteger.ZERO, Gender.FEMALE));
    assertEquals("ноль", NumberUtils.integerToWords(BigInteger.ZERO, Gender.NEUTER));
  }

  @Test
  public void integerToWordsOneTest() {
    assertEquals("один", NumberUtils.integerToWords(BigInteger.ONE, Gender.MALE));
    assertEquals("одна", NumberUtils.integerToWords(BigInteger.ONE, Gender.FEMALE));
    assertEquals("одно", NumberUtils.integerToWords(BigInteger.ONE, Gender.NEUTER));
  }

  @Test
  public void integerToWords234Test() {
    assertEquals("два", NumberUtils.integerToWords(2, Gender.MALE));
    assertEquals("две", NumberUtils.integerToWords(2, Gender.FEMALE));
    assertEquals("два", NumberUtils.integerToWords(2, Gender.NEUTER));

    assertEquals("три", NumberUtils.integerToWords(3, Gender.MALE));
    assertEquals("три", NumberUtils.integerToWords(3, Gender.FEMALE));
    assertEquals("три", NumberUtils.integerToWords(3, Gender.NEUTER));
  }

  @Test
  public void integerToWords5Test() {
    assertEquals("пять", NumberUtils.integerToWords(5, Gender.MALE));
    assertEquals("пять", NumberUtils.integerToWords(5, Gender.FEMALE));
    assertEquals("пять", NumberUtils.integerToWords(5, Gender.NEUTER));
  }

  @Test
  public void integerToWordsTenTest() {
    assertEquals("десять", NumberUtils.integerToWords(BigInteger.TEN, Gender.MALE));
    assertEquals("десять", NumberUtils.integerToWords(BigInteger.TEN, Gender.FEMALE));
    assertEquals("десять", NumberUtils.integerToWords(BigInteger.TEN, Gender.NEUTER));
  }

  @Test
  public void integerToWords40Test() {
    assertEquals("сорок", NumberUtils.integerToWords(new BigInteger("40"), Gender.MALE));
    assertEquals("сорок", NumberUtils.integerToWords(new BigInteger("40"), Gender.FEMALE));
    assertEquals("сорок", NumberUtils.integerToWords(new BigInteger("40"), Gender.NEUTER));
  }

  @Test
  public void integerToWords90Test() {
    assertEquals("девяносто", NumberUtils.integerToWords(new BigInteger("90"), Gender.MALE));
    assertEquals("девяносто", NumberUtils.integerToWords(new BigInteger("90"), Gender.FEMALE));
    assertEquals("девяносто", NumberUtils.integerToWords(new BigInteger("90"), Gender.NEUTER));
  }

  @Test
  public void integerToWordsRandomTest() {
    assertEquals(
        "семьдесят шесть триллионов пятьсот восемьдесят один миллиард семьсот девяносто миллионов сто тридцать две тысячи четыреста шестьдесят пять",
        NumberUtils.integerToWords(76581790132465L, Gender.MALE));

    assertEquals(
        "семьдесят шесть триллионов пятьсот восемьдесят один миллиард семьсот девяносто миллионов сто тридцать две тысячи четыреста шестьдесят один",
        NumberUtils.integerToWords(76581790132461L, Gender.MALE));
    assertEquals(
        "семьдесят шесть триллионов пятьсот восемьдесят один миллиард семьсот девяносто миллионов сто тридцать две тысячи четыреста шестьдесят одна",
        NumberUtils.integerToWords(76581790132461L, Gender.FEMALE));

    assertEquals(
        "семьдесят шесть триллионов пятьсот восемьдесят один миллиард семьсот девяносто миллионов сто тридцать две тысячи четыреста шестьдесят две",
        NumberUtils.integerToWords(76581790132462L, Gender.FEMALE));

    assertEquals(
        "девятьсот девяносто тысяч семьсот девятнадцать",
        NumberUtils.integerToWords(990719L, Gender.FEMALE));
  }

  @Test
  public void integerToWordsNullTest() {
    assertNull(NumberUtils.integerToWords(null, Gender.MALE));
    assertNull(NumberUtils.integerToWords(null, null));
  }

  @Test
  public void fractionToWordsOneOfTest() {
    assertEquals("один десятый", NumberUtils.fractionToWords(0.1, Gender.MALE));
    assertEquals("один сотый", NumberUtils.fractionToWords(0.01, Gender.MALE));
    assertEquals("один тысячный", NumberUtils.fractionToWords(0.001, Gender.MALE));
    assertEquals("один десятитысячный", NumberUtils.fractionToWords(0.0001, Gender.MALE));
    assertEquals("один стотысячный", NumberUtils.fractionToWords(0.00001, Gender.MALE));

    assertEquals("одна десятая", NumberUtils.fractionToWords(0.1, Gender.FEMALE));
    assertEquals("одна сотая", NumberUtils.fractionToWords(0.01, Gender.FEMALE));
    assertEquals("одна тысячная", NumberUtils.fractionToWords(0.001, Gender.FEMALE));
    assertEquals("одна десятитысячная", NumberUtils.fractionToWords(0.0001, Gender.FEMALE));
    assertEquals("одна стотысячная", NumberUtils.fractionToWords(0.00001, Gender.FEMALE));
  }

  @Test
  public void fractionToWordsNullTest() {
    assertNull(NumberUtils.fractionToWords(null, Gender.MALE));
    assertNull(NumberUtils.fractionToWords(null, null));
  }

  @Test
  public void numberToWordsTest() {
    assertThat(NumberUtils.numberToWords(new BigDecimal("11.12"), Gender.MALE)).isEqualTo("одиннадцать целых двенадцать сотых");
    assertThat(NumberUtils.numberToWords(new BigDecimal("1.12"), Gender.MALE)).isEqualTo("один целый двенадцать сотых");
    assertThat(NumberUtils.numberToWords(new BigDecimal("1.12"), Gender.FEMALE)).isEqualTo("одна целая двенадцать сотых");
  }

  @Test
  public void fractionToWordsTest() {
    assertThat(NumberUtils.numberToWords(new BigDecimal("0.11"), Gender.MALE)).isEqualTo("ноль целых одиннадцать сотых");
    assertThat(NumberUtils.numberToWords(new BigDecimal("0.11"), Gender.MALE)).isEqualTo("ноль целых одиннадцать сотых");
    assertThat(NumberUtils.numberToWords(new BigDecimal("0.11"), Gender.FEMALE)).isEqualTo("ноль целых одиннадцать сотых");
  }

  @Test
  public void currencyTest() {
    BigDecimal value = BigDecimal.valueOf(10.15D);
    int k =
        value
            .multiply(BigDecimal.valueOf(100))
            .remainder(BigDecimal.valueOf(100))
            .setScale(0, RoundingMode.HALF_UP)
            .intValue();
    BigInteger rub = value.setScale(0, RoundingMode.DOWN).toBigInteger();
    assertEquals(
        "десять рублей 15 копеек",
        NumberUtils.integerToWords(rub, Gender.MALE)
            + " "
            + PluralVariants.by("рубль", "рубля", "рублей").resolve(rub)
            + " "
            + k
            + " "
            + PluralVariants.by("копейка", "копейки", "копеек").resolve(k));
  }
}
