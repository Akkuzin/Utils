package aaa.basic;

import aaa.basis.LevinshteinTransformation;
import aaa.basis.LevinshteinTransformation.IOperation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class LesinshteinTransformationTest {

	@Test
	public void simpleTest() {
		testTransformation(asList(1, 2, 3), asList(1, 3, 4));
		testTransformation(asList(1, 3), asList(1, 2, 3));
		testTransformation(asList(1, 4, 3), asList(1, 2, 3));
		testTransformation(asList(1, 2, 3, 4, 5, 6, 7, 8), asList(9, 8, 7, 6, 5, 4, 3, 2));
		testTransformation(asList(1, 2, 3), asList(2, 1, 9));
		testTransformation(asList(1, 2, 3), asList(1, 2, 1));

	}

	private static <T> void testTransformation(List<T> from, List<T> to) {
		List<T> result = new ArrayList<>(from);
		LevinshteinTransformation.levenshteinDistance(	from,
														to,
														LevinshteinTransformation.DEFAULT_PARAMETERS)
				.stream()
				.map(IOperation::getAction)
				.forEach(f -> f.apply(result));
		assertEquals(to, result);
	}

}
