/** */
package aaa.nvl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class NvlTest {

  @Test
  public void minTest() {
    assertThat(Nvl.min("123", "")).isEqualTo("");
    assertThat(Nvl.min("", "123")).isEqualTo("");
    assertThat(Nvl.min("123", null)).isEqualTo("123");
    assertThat(Nvl.min(null, "123")).isEqualTo("123");
    assertThat(Nvl.min(null, 123)).isEqualTo(123);
    assertThat(Nvl.min(123, null)).isEqualTo(123);
    assertThat(Nvl.min(123, 123)).isEqualTo(123);
    assertThat(Nvl.min(123, -123)).isEqualTo(-123);
    assertThat(Nvl.min(-123, 123)).isEqualTo(-123);
    assertThat(Nvl.min((Integer) null, null)).isNull();
    assertThat(Nvl.min(123, -321)).isEqualTo(-321);
    assertThat(Nvl.min(123, 321)).isEqualTo(123);
    assertThat(Nvl.min(-123, 321)).isEqualTo(-123);
  }

  @Test
  public void maxTest() {
    assertThat(Nvl.max("123", "")).isEqualTo("123");
    assertThat(Nvl.max("", "123")).isEqualTo("123");
    assertThat(Nvl.max("123", null)).isEqualTo("123");
    assertThat(Nvl.max(null, "123")).isEqualTo("123");
    assertThat(Nvl.max(null, 123)).isEqualTo(123);
    assertThat(Nvl.max(123, null)).isEqualTo(123);
    assertThat(Nvl.max(123, 123)).isEqualTo(123);
    assertThat(Nvl.max(123, -123)).isEqualTo(123);
    assertThat(Nvl.max(-123, 123)).isEqualTo(123);
    assertThat(Nvl.max((Integer) null, null)).isNull();
    assertThat(Nvl.max(123, -321)).isEqualTo(123);
    assertThat(Nvl.max(123, 321)).isEqualTo(321);
    assertThat(Nvl.max(-123, 321)).isEqualTo(321);
  }

  @Test
  public void nvlTest() {
    assertThat(Nvl.nvl("123", "234")).isEqualTo("123");
    assertThat(Nvl.nvl(null, "234")).isEqualTo("234");
    assertNull(Nvl.nvl(null, null));
  }

  @Test
  public void coalesceTest() {
    assertThat(Nvl.coalesce("123", "234", "567")).isEqualTo("123");
    assertThat(Nvl.coalesce("123", null, "567")).isEqualTo("123");
    assertThat(Nvl.coalesce(null, "234", null)).isEqualTo("234");
    assertThat(Nvl.coalesce(null, null, "567")).isEqualTo("567");
    assertThat(Nvl.coalesce(null, null, "567", null)).isEqualTo("567");
    assertThat(Nvl.coalesce(null, null, "567", "999")).isEqualTo("567");
    assertThat(Nvl.coalesce(null, null, null)).isEqualTo(Optional.empty());
  }
}
