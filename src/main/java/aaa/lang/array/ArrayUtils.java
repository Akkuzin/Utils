package aaa.lang.array;

public class ArrayUtils {

	/* Reverse elements order in array part */

	public static <T> void reverse(T[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(int[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(long[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(short[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(byte[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(char[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(float[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	public static void reverse(double[] array, final int first, final int last) {
		for (int i = first, j = last - 1; i < j; ++i, --j) {
			swap(array, i, j);
		}
	}

	/* Swap two elements of array */

	public static <T> void swap(T[] array, final int first, final int second) {
		final T value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(int[] array, final int first, final int second) {
		final int value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(long[] array, final int first, final int second) {
		final long value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(short[] array, final int first, final int second) {
		final short value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(byte[] array, final int first, final int second) {
		final byte value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(char[] array, final int first, final int second) {
		char value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(float[] array, final int first, final int second) {
		final float value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	public static void swap(double[] array, final int first, final int second) {
		final double value = array[first];
		array[first] = array[second];
		array[second] = value;
	}

	/** Move element in array */
	public static <T> void move(T[] array, final int from, final int to) {
		if (from != to) {
			T value = array[from];
			int range = Math.abs(from - to);

			if (from < to) {
				switch (range) {
				case 2:
					array[from] = array[from + 1];
					//$FALL-THROUGH$
				case 1:
					array[to - 1] = array[to];
					break;
				default:
					System.arraycopy(array, from + 1, array, from, range);
				}
			} else {
				switch (range) {
				case 2:
					array[from] = array[from - 1];
					//$FALL-THROUGH$
				case 1:
					array[to + 1] = array[to];
					break;
				default:
					System.arraycopy(array, to, array, to + 1, range);
				}
			}
			array[to] = value;
		}
	}
}
