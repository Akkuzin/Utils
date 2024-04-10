package aaa.lambda;

import static aaa.lambda.LambdaUtils.range;
import static aaa.lambda.LambdaUtils.unorderedBatches;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class LambdaUtilsTest {

  @Test
  public void rangeTest() {
    assertThat(range(1, 6)).containsExactly(1, 2, 3, 4, 5);
    assertThat(range(2, -3)).containsExactly(2, 1, 0, -1, -2);
    assertThat(range(-5, 1)).containsExactly(-5, -4, -3, -2, -1, 0);
  }

  @Test
  public void testBatchSplit() {
    assertThat(
            IntStream.range(0, 11).boxed().collect(unorderedBatches(3, ArrayList::new, toList())))
        .isEqualTo(List.of(List.of(0, 1, 2), List.of(3, 4, 5), List.of(6, 7, 8), List.of(9, 10)));
    assertThat(IntStream.range(0, 11).boxed().collect(unorderedBatches(3, HashSet::new, toList())))
        .isEqualTo(List.of(Set.of(0, 1, 2), Set.of(3, 4, 5), Set.of(6, 7, 8), Set.of(9, 10)));
    assertThat(
            Stream.of(0, 0, 1, 2, 3, 4, 5, 7, 0)
                .collect(unorderedBatches(3, HashSet::new, toList())))
        .isEqualTo(List.of(Set.of(0, 1, 2), Set.of(3, 4, 5), Set.of(7, 0)));
  }
}
