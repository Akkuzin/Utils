package aaa.threadlocal;

import java.text.NumberFormat;

public abstract class NotSettableThreadLocal<T> extends ThreadLocal<T> {
  public final void set(NumberFormat value) {
    throw new UnsupportedOperationException();
  }
}
