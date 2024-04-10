package aaa.basic.text;

import static org.assertj.core.api.Assertions.assertThat;

import aaa.basis.text.SimpleMonospaceWordWrapper;
import org.junit.jupiter.api.Test;

public class SimpleMonospaceWordWrapperTest {

  @Test
  public void splitByMaxLengthTest() {
    assertThat(
            SimpleMonospaceWordWrapper.builder()
                .delimiter(" ")
                .maxLength(MAX_LENGTH_FOR_SPLIT_3)
                .separatorChars("\n\r \t")
                .build()
                .apply("aaa\naaa\naaa\naa"))
        .containsExactly("aaa", "aaa", "aaa", "aa");

    assertThat(
            SimpleMonospaceWordWrapper.builder()
                .delimiter(" ")
                .maxLength(MAX_LENGTH_FOR_SPLIT_3)
                .separatorChars("\n\r \t")
                .build()
                .apply("aaaaaaaaaaa"))
        .containsExactly("aaa", "aaa", "aaa", "aa");

    assertThat(
            SimpleMonospaceWordWrapper.builder()
                .delimiter(" ")
                .maxLength(MAX_LENGTH_FOR_SPLIT_5)
                .separatorChars("\n\r \t")
                .build()
                .apply("aa bb aaaa a aaaa"))
        .containsExactly("aa bb", "aaaa", "a", "aaaa");
  }

  static final int MAX_LENGTH_FOR_SPLIT_3 = 3;
  static final int MAX_LENGTH_FOR_SPLIT_5 = 5;
}
