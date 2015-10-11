package aaa.basic.sort;

import aaa.basis.sort.SortUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class SortUtilsTest {

	@Test
	public void findTest1() {
		doFindTest(new Integer[] { 1, 2, 3 }, new Integer[] { 1, 2, 2, 3 }, 2);
		doFindTest(new Integer[] { 2, 2, 2 }, new Integer[] { 2, 2, 2, 2 }, 2);
		doFindTest(new Integer[] { 1, 1, 1 }, new Integer[] { 1, 1, 1, 2 }, 2);
		doFindTest(new Integer[] { 3, 3, 3 }, new Integer[] { 2, 3, 3, 3 }, 2);
	}

	public <T extends Comparable<T>> void doFindTest(T[] array, T[] expect, T value) {
		List<T> baseList = new ArrayList<>();
		baseList.addAll(asList(array));
		baseList.add(SortUtils.findPlace(array, value, 0, 3), value);
		assertEquals(asList(expect), baseList);
	}
}
