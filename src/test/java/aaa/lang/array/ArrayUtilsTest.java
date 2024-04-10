package aaa.lang.array;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {

  @Test
  public void moveForwardTest() {
    Integer[] arrayResult = {1, 2, 3};
    Integer[] arrayExpect = {2, 3, 1};
    ArrayUtils.move(arrayResult, 0, 2);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void moveBackwardTest() {
    Integer[] arrayResult = {1, 2, 3};
    Integer[] arrayExpect = {3, 1, 2};
    ArrayUtils.move(arrayResult, 2, 0);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void moveInPlaceTest() {
    Integer[] arrayResult = {1, 2, 3};
    Integer[] arrayExpect = {1, 2, 3};
    ArrayUtils.move(arrayResult, 0, 0);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void moveAverageDistanceBackwardTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Integer[] arrayExpect = {1, 2, 10, 3, 4, 5, 6, 7, 8, 9};
    ArrayUtils.move(arrayResult, 9, 2);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void moveAverageDistanceForwardTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Integer[] arrayExpect = {1, 2, 4, 5, 6, 7, 8, 9, 3, 10};
    ArrayUtils.move(arrayResult, 2, 8);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void swapFirstAndLastTest() {
    Integer[] arrayResult = {1, 2, 3};
    Integer[] arrayExpect = {3, 2, 1};
    ArrayUtils.swap(arrayResult, 0, 2);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void swapTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {1, 6, 3, 4, 5, 2, 7, 8};
    ArrayUtils.swap(arrayResult, 1, 5);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void swapFirstTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {6, 2, 3, 4, 5, 1, 7, 8};
    ArrayUtils.swap(arrayResult, 0, 5);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void swapLastTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {1, 2, 3, 8, 5, 6, 7, 4};
    ArrayUtils.swap(arrayResult, 3, 7);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void reverseAllTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {8, 7, 6, 5, 4, 3, 2, 1};
    ArrayUtils.reverse(arrayResult, 0, arrayResult.length);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void reverseFirstTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {5, 4, 3, 2, 1, 6, 7, 8};
    ArrayUtils.reverse(arrayResult, 0, 5);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void reverseLastTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {1, 2, 8, 7, 6, 5, 4, 3};
    ArrayUtils.reverse(arrayResult, 2, arrayResult.length);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }

  @Test
  public void reverseSomeTest() {
    Integer[] arrayResult = {1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] arrayExpect = {1, 5, 4, 3, 2, 6, 7, 8};
    ArrayUtils.reverse(arrayResult, 1, 5);
    assertThat(arrayResult).containsExactly(arrayExpect);
  }
}
