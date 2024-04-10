package aaa.lang.reflection;

@FunctionalInterface
public interface ObjBooleanConsumer<T> {
  void accept(T object, boolean value);
}
