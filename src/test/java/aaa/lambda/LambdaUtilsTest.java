package aaa.lambda;

import org.junit.Test;

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

}