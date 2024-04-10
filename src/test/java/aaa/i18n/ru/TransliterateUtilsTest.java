package aaa.i18n.ru;

import static aaa.i18n.ru.TransliterateUtils.variateFix;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TransliterateUtilsTest {

  @Test
  void testTranslateComma() {
    assertThat(variateFix(",hbuf")).containsOnly("брига", ",hbuf");
  }
}
