/**
 * 
 */
package aaa.lang.collection;

import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Predicate;

public class CollectionUtils {

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static <T> int indexOf(@NonNull List<T> entityList, @NonNull Predicate<T> predicate) {
		for (ListIterator<T> iterator = entityList.listIterator(); iterator.hasNext();) {
			if (predicate.test(iterator.next())) {
				return iterator.previousIndex();
			}
		}
		return -1;
	}

}
