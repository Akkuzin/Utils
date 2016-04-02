package aaa.lambda;

import aaa.nvl.Nvl;
import org.junit.Test;

import java.util.List;

import static aaa.lambda.LambdaUtils.comparator;
import static aaa.lambda.LambdaUtils.range;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class LambdaUtilsTest {

	@Test
	public void rangeTest() {
		assertEquals(asList(1, 2, 3, 4, 5), range(1, 6).boxed().collect(toList()));
		assertEquals(asList(2, 1, 0, -1, -2), range(2, -3).boxed().collect(toList()));
		assertEquals(asList(-5, -4, -3, -2, -1, 0), range(-5, 1).boxed().collect(toList()));
	}

	@Test
	public void comparatorTest() {
		assertEquals(	asList(asList("info"), asList("some text"), asList("zzz")),
						asList(asList("info"), asList("zzz"), asList("some text")).stream()
								.sorted(comparator(Nvl::nvlGetFirst))
								.collect(toList()));
		assertEquals(	asList(asList("zzz"), asList("some text"), asList("info")),
						asList(asList("info"), asList("zzz"), asList("some text")).stream()
								.sorted(LambdaUtils.<List<String>, String> comparator(Nvl::nvlGetFirst)
										.reversed())
								.collect(toList()));
	}

}