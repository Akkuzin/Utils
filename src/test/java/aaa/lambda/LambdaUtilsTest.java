package aaa.lambda;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static aaa.lambda.LambdaUtils.range;
import static aaa.lambda.LambdaUtils.unorderedBatches;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class LambdaUtilsTest {

	@Test
	public void rangeTest() {
		assertEquals(asList(1, 2, 3, 4, 5), range(1, 6).boxed().collect(toList()));
		assertEquals(asList(2, 1, 0, -1, -2), range(2, -3).boxed().collect(toList()));
		assertEquals(asList(-5, -4, -3, -2, -1, 0), range(-5, 1).boxed().collect(toList()));
	}

	@Test
	public void testBatchSplit() {
		assertEquals(asList(asList(0, 1, 2), asList(3, 4, 5), asList(6, 7, 8), asList(9)), IntStream.range(0, 10).boxed().collect(
				unorderedBatches(3, ArrayList::new, toList())));
		assertEquals(asList(ImmutableSet.of(0, 1, 2), ImmutableSet.of(3, 4, 5), ImmutableSet.of(6, 7, 8), ImmutableSet.of(9)), IntStream.range(0, 10).boxed().collect(
				unorderedBatches(3, HashSet::new, toList())));
		assertEquals(asList(ImmutableSet.of(0, 1, 2), ImmutableSet.of(3, 4, 5), ImmutableSet.of(7, 0)), Stream.of(0, 0, 1, 2, 3, 4, 5, 7, 0).collect(
				unorderedBatches(3, HashSet::new, toList())));
	}
}