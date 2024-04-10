package aaa.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelUtils {

  public static final int DEFAULT_CPU_CORES = Runtime.getRuntime().availableProcessors();

  public static ForkJoinPool makePool(String namePrefix, int parallelism) {
    AtomicInteger index = new AtomicInteger(0);
    return new ForkJoinPool(
        parallelism,
        pool -> {
          ForkJoinWorkerThread worker =
              ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
          worker.setName(namePrefix + "-" + index.getAndIncrement());
          return worker;
        },
        null,
        false);
  }
}
