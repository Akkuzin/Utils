package aaa.lambda;

import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LambdaUtils {

	public static <T> Function<Object, T> caster(@NonNull Class<T> clazz) {
		return v -> clazz.isAssignableFrom(v.getClass()) ? clazz.cast(v) : null;
	}

	public static <T> Function<T, T> peek(@NonNull Consumer<T> consumer) {
		return t -> {
			consumer.accept(t);
			return t;
		};
	}

	public static <T> Function<T, T> doIf(Predicate<T> condition, @NonNull Function<T, T> action) {
		return t -> condition.test(t) ? action.apply(t) : t;
	}

	public static IntStream range(int startInclusive, int endExclusive) {
		if (startInclusive > endExclusive) {
			int diff = startInclusive - endExclusive;
			return IntStream.range(0, diff).map(i -> startInclusive - i);
		} else {
			return IntStream.range(startInclusive, endExclusive);
		}
	}

	public static <R> Predicate<R> not(Predicate<R> predicate) {
		return predicate.negate();
	}

	public static <T> Predicate<T> predicate(Predicate<T> predicate) {
		return predicate;
	}

	@FunctionalInterface
	public interface ThrowingFunction<T, R> {
		R apply(T t) throws Exception;
	}

	@FunctionalInterface
	public interface ThrowingConsumer<T> {
		void accept(T t) throws Exception;
	}

	@FunctionalInterface
	public interface ThrowingSupplier<T> {
		T get() throws Exception;
	}

	@SuppressWarnings("unchecked")
	static <T extends Exception, R> R sneakyThrow(Exception t) throws T {
		throw (T) t;
	}

	public static <T, R> Function<T, R> sneakyThrows(ThrowingFunction<T, R> f) {
		return t -> {
			try {
				return f.apply(t);
			} catch (Exception ex) {
				return sneakyThrow(ex);
			}
		};
	}

	public static <T> Consumer<T> sneakyThrows(ThrowingConsumer<T> c) {
		return t -> {
			try {
				c.accept(t);
			} catch (Exception ex) {
				sneakyThrow(ex);
			}
		};
	}

	public static <T> Supplier<T> sneakyThrows(ThrowingSupplier<T> c) {
		return () -> {
			try {
				return c.get();
			} catch (Exception ex) {
				return sneakyThrow(ex);
			}
		};
	}
}
