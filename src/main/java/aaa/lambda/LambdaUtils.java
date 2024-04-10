package aaa.lambda;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import lombok.NonNull;

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
  public interface ThrowingBiFunction<T, Y, R> {
    R apply(T t, Y y) throws Exception;
  }

  @FunctionalInterface
  public interface ThrowingConsumer<T> {
    void accept(T t) throws Exception;
  }

  @FunctionalInterface
  public interface ThrowingBiConsumer<T, Y> {
    void accept(T t, Y y) throws Exception;
  }

  @FunctionalInterface
  public interface ThrowingSupplier<T> {
    T get() throws Exception;
  }

  @FunctionalInterface
  public interface ThrowingRunnable {
    void run() throws Exception;
  }

  @FunctionalInterface
  public interface ThrowingCallable<V> {
    V call() throws Exception;
  }

  @SuppressWarnings("unchecked")
  static <T extends Exception, R> R sneakyThrow(Exception t) throws T {
    throw (T) t;
  }

  public static <T, R> Function<T, R> sneakyThrowsFunction(LambdaUtils.ThrowingFunction<T, R> f) {
    return t -> {
      try {
        return f.apply(t);
      } catch (Exception ex) {
        return sneakyThrow(ex);
      }
    };
  }

  public static <T, Y, R> BiFunction<T, Y, R> sneakyThrowsFunction(
      LambdaUtils.ThrowingBiFunction<T, Y, R> f) {
    return (t, y) -> {
      try {
        return f.apply(t, y);
      } catch (Exception ex) {
        return sneakyThrow(ex);
      }
    };
  }

  public static <T> Consumer<T> sneakyThrowsConsumer(LambdaUtils.ThrowingConsumer<T> c) {
    return t -> {
      try {
        c.accept(t);
      } catch (Exception ex) {
        sneakyThrow(ex);
      }
    };
  }

  public static <T, Y> BiConsumer<T, Y> sneakyThrowsConsumer(
      LambdaUtils.ThrowingBiConsumer<T, Y> c) {
    return (t, y) -> {
      try {
        c.accept(t, y);
      } catch (Exception ex) {
        sneakyThrow(ex);
      }
    };
  }

  public static <T> Supplier<T> sneakyThrowsSupplier(LambdaUtils.ThrowingSupplier<T> c) {
    return () -> {
      try {
        return c.get();
      } catch (Exception ex) {
        return sneakyThrow(ex);
      }
    };
  }

  public static Runnable sneakyThrowsRunnable(LambdaUtils.ThrowingRunnable r) {
    return () -> {
      try {
        r.run();
      } catch (Exception ex) {
        sneakyThrow(ex);
      }
    };
  }

  public static <V> Callable<V> sneakyThrowsCallable(LambdaUtils.ThrowingCallable<V> c) {
    return () -> {
      try {
        return c.call();
      } catch (Exception ex) {
        return sneakyThrow(ex);
      }
    };
  }

  public static <T, A, R, C extends Collection<T>> Collector<T, ?, R> unorderedBatches(
      int batchSize, Supplier<C> containerSupplier, Collector<C, A, R> downstream) {
    class Acc {
      C data = containerSupplier.get();
      A accumulator = downstream.supplier().get();
    }
    BiConsumer<Acc, T> accumulator =
        (acc, t) -> {
          acc.data.add(t);
          if (acc.data.size() >= batchSize) {
            downstream.accumulator().accept(acc.accumulator, acc.data);
            acc.data = containerSupplier.get();
          }
        };
    return Collector.of(
        Acc::new,
        accumulator,
        (acc1, acc2) -> {
          acc1.accumulator = downstream.combiner().apply(acc1.accumulator, acc2.accumulator);
          for (T t : acc2.data) {
            accumulator.accept(acc1, t);
          }
          return acc1;
        },
        acc -> {
          if (!acc.data.isEmpty()) {
            downstream.accumulator().accept(acc.accumulator, acc.data);
          }
          return downstream.finisher().apply(acc.accumulator);
        },
        Collector.Characteristics.UNORDERED);
  }
}
