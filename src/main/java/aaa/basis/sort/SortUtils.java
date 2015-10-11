package aaa.basis.sort;

import java.util.Comparator;

public class SortUtils {

	public static <T> int findPlace(final T[] array,
									final T value,
									final Comparator<T> comparator,
									final int firstBound,
									final int lastBound) {
		int low = firstBound;
		int high = lastBound - 1;

		while (low < high) {
			int mid = (low + high) >>> 1;
			T midVal = array[mid];
			int cmp = comparator.compare(midVal, value);

			if (cmp < 0) {
				low = mid + 1;
			} else if (cmp > 0) {
				high = mid - 1;
			} else {
				low = mid; // key found
				break;
			}
		}
		return comparator.compare(value, array[low]) > 0 ? low + 1 : low;
	}

	public static <T extends Comparable<T>> int findPlace(	final T[] array,
															final T value,
															final int firstBound,
															final int lastBound) {
		return findPlace(array, value, Comparable::compareTo, firstBound, lastBound);
	}
}
