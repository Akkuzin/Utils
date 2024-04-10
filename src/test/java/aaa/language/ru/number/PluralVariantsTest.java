package aaa.language.ru.number;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PluralVariantsTest {

  @Test
  public void testWithHours() {
    PluralVariants variants = PluralVariants.by("час", "часа", "часов");
    assertEquals("0 часов", "0 " + variants.resolve(0));
    assertEquals("1 час", "1 " + variants.resolve(1));
    assertEquals("2 часа", "2 " + variants.resolve(2));
    assertEquals("3 часа", "3 " + variants.resolve(3));
    assertEquals("4 часа", "4 " + variants.resolve(4));
    assertEquals("5 часов", "5 " + variants.resolve(5));
    assertEquals("6 часов", "6 " + variants.resolve(6));
    assertEquals("7 часов", "7 " + variants.resolve(7));
    assertEquals("8 часов", "8 " + variants.resolve(8));
    assertEquals("9 часов", "9 " + variants.resolve(9));
    assertEquals("10 часов", "10 " + variants.resolve(10));
    assertEquals("11 часов", "11 " + variants.resolve(11));
    assertEquals("12 часов", "12 " + variants.resolve(12));
    assertEquals("13 часов", "13 " + variants.resolve(13));
    assertEquals("14 часов", "14 " + variants.resolve(14));
    assertEquals("15 часов", "15 " + variants.resolve(15));
    assertEquals("16 часов", "16 " + variants.resolve(16));
    assertEquals("17 часов", "17 " + variants.resolve(17));
    assertEquals("18 часов", "18 " + variants.resolve(18));
    assertEquals("19 часов", "19 " + variants.resolve(19));
    assertEquals("20 часов", "20 " + variants.resolve(20));
    assertEquals("21 час", "21 " + variants.resolve(21));
    assertEquals("22 часа", "22 " + variants.resolve(22));
    assertEquals("12322 часа", "12322 " + variants.resolve(12322));
  }

  @Test
  public void testWithClouds() {
    PluralVariants variants = PluralVariants.by("облако", "облака", "облаков");
    assertEquals("0 облаков", "0 " + variants.resolve(0));
    assertEquals("1 облако", "1 " + variants.resolve(1));
    assertEquals("2 облака", "2 " + variants.resolve(2));
    assertEquals("3 облака", "3 " + variants.resolve(3));
    assertEquals("4 облака", "4 " + variants.resolve(4));
    assertEquals("5 облаков", "5 " + variants.resolve(5));
    assertEquals("6 облаков", "6 " + variants.resolve(6));
    assertEquals("7 облаков", "7 " + variants.resolve(7));
    assertEquals("8 облаков", "8 " + variants.resolve(8));
    assertEquals("9 облаков", "9 " + variants.resolve(9));
    assertEquals("10 облаков", "10 " + variants.resolve(10));
    assertEquals("11 облаков", "11 " + variants.resolve(11));
    assertEquals("12 облаков", "12 " + variants.resolve(12));
    assertEquals("13 облаков", "13 " + variants.resolve(13));
    assertEquals("14 облаков", "14 " + variants.resolve(14));
    assertEquals("15 облаков", "15 " + variants.resolve(15));
    assertEquals("16 облаков", "16 " + variants.resolve(16));
    assertEquals("17 облаков", "17 " + variants.resolve(17));
    assertEquals("18 облаков", "18 " + variants.resolve(18));
    assertEquals("19 облаков", "19 " + variants.resolve(19));
    assertEquals("20 облаков", "20 " + variants.resolve(20));
    assertEquals("21 облако", "21 " + variants.resolve(21));
    assertEquals("22 облака", "22 " + variants.resolve(22));
    assertEquals("22221 облако", "22221 " + variants.resolve(22221));
  }

  @Test
  public void testWithCats() {
    PluralVariants variants = PluralVariants.by("кошка", "кошки", "кошек");
    assertEquals("0 кошек", "0 " + variants.resolve(0));
    assertEquals("1 кошка", "1 " + variants.resolve(1));
    assertEquals("2 кошки", "2 " + variants.resolve(2));
    assertEquals("3 кошки", "3 " + variants.resolve(3));
    assertEquals("4 кошки", "4 " + variants.resolve(4));
    assertEquals("5 кошек", "5 " + variants.resolve(5));
    assertEquals("6 кошек", "6 " + variants.resolve(6));
    assertEquals("7 кошек", "7 " + variants.resolve(7));
    assertEquals("8 кошек", "8 " + variants.resolve(8));
    assertEquals("9 кошек", "9 " + variants.resolve(9));
    assertEquals("10 кошек", "10 " + variants.resolve(10));
    assertEquals("11 кошек", "11 " + variants.resolve(11));
    assertEquals("12 кошек", "12 " + variants.resolve(12));
    assertEquals("13 кошек", "13 " + variants.resolve(13));
    assertEquals("14 кошек", "14 " + variants.resolve(14));
    assertEquals("15 кошек", "15 " + variants.resolve(15));
    assertEquals("16 кошек", "16 " + variants.resolve(16));
    assertEquals("17 кошек", "17 " + variants.resolve(17));
    assertEquals("18 кошек", "18 " + variants.resolve(18));
    assertEquals("19 кошек", "19 " + variants.resolve(19));
    assertEquals("20 кошек", "20 " + variants.resolve(20));
    assertEquals("21 кошка", "21 " + variants.resolve(21));
    assertEquals("22 кошки", "22 " + variants.resolve(22));
    assertEquals("101 кошка", "101 " + variants.resolve(101));
  }
}
