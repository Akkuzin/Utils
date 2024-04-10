package aaa.basis.text;

import static aaa.basis.text.StringFunc.ellipsis;
import static aaa.basis.text.StringFunc.substringAfterLastIfExists;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringFuncTest {

  @Test
  public void substringAfterLastIfExistsTest() {
    assertNull(substringAfterLastIfExists(null, "."));
    assertEquals("", substringAfterLastIfExists("", "."));
    assertEquals("", substringAfterLastIfExists("ASDQWEZX", null));
    assertEquals("", substringAfterLastIfExists("ASDQWEZX", ""));
    assertEquals("ASD", substringAfterLastIfExists("ASD", "."));
    assertEquals("QWE", substringAfterLastIfExists("ASD.QWE", "."));
    assertEquals("QWE", substringAfterLastIfExists(".QWE", "."));
    assertEquals("ZXC", substringAfterLastIfExists("ASD.QWE.ZXC", "."));
    assertEquals("", substringAfterLastIfExists("ASD.QWE.", "."));
    assertEquals("ZXC", substringAfterLastIfExists("ASDQWEZXC", "QWE"));
    assertEquals("", substringAfterLastIfExists("ASDQWE", "QWE"));
  }

  @Test
  public void testEllipsis() {
    assertThat(ellipsis("abcdef", 5)).isEqualTo("abcd…");
    assertThat(ellipsis("abcdef", 0)).isEqualTo("");
    assertThat(ellipsis("abcdef", 1)).isEqualTo("…");
    assertThat(ellipsis(null, 1)).isNull();
  }
}
