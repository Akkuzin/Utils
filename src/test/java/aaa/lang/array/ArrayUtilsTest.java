package aaa.lang.array;

import static org.junit.Assert.assertArrayEquals;


import org.junit.Test;

public class ArrayUtilsTest {

	@Test
	public void moveForwardTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3 };
		Integer[] arrayExpect = new Integer[] { 2, 3, 1 };
		ArrayUtils.move(arrayResult, 0, 2);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void moveBackwardTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3 };
		Integer[] arrayExpect = new Integer[] { 3, 1, 2 };
		ArrayUtils.move(arrayResult, 2, 0);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void moveInPlaceTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3 };
		Integer[] arrayExpect = new Integer[] { 1, 2, 3 };
		ArrayUtils.move(arrayResult, 0, 0);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void moveAverageDistanceBackwardTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] arrayExpect = new Integer[] { 1, 2, 10, 3, 4, 5, 6, 7, 8, 9 };
		ArrayUtils.move(arrayResult, 9, 2);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void moveAverageDistanceForwardTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] arrayExpect = new Integer[] { 1, 2, 4, 5, 6, 7, 8, 9, 3, 10 };
		ArrayUtils.move(arrayResult, 2, 8);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void swapFirstAndLastTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3 };
		Integer[] arrayExpect = new Integer[] { 3, 2, 1 };
		ArrayUtils.swap(arrayResult, 0, 2);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void swapTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 1, 6, 3, 4, 5, 2, 7, 8 };
		ArrayUtils.swap(arrayResult, 1, 5);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void swapFirstTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 6, 2, 3, 4, 5, 1, 7, 8 };
		ArrayUtils.swap(arrayResult, 0, 5);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void swapLastTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 1, 2, 3, 8, 5, 6, 7, 4 };
		ArrayUtils.swap(arrayResult, 3, 7);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void reverseAllTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 8, 7, 6, 5, 4, 3, 2, 1 };
		ArrayUtils.reverse(arrayResult, 0, arrayResult.length);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void reverseFirstTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 5, 4, 3, 2, 1, 6, 7, 8 };
		ArrayUtils.reverse(arrayResult, 0, 5);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void reverseLastTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 1, 2, 8, 7, 6, 5, 4, 3 };
		ArrayUtils.reverse(arrayResult, 2, arrayResult.length);
		assertArrayEquals(arrayExpect, arrayResult);
	}

	@Test
	public void reverseSomeTest() {
		Integer[] arrayResult = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] arrayExpect = new Integer[] { 1, 5, 4, 3, 2, 6, 7, 8 };
		ArrayUtils.reverse(arrayResult, 1, 5);
		assertArrayEquals(arrayExpect, arrayResult);
	}

}
