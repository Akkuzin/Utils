package aaa.lang.reflection;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;

import aaa.lang.reflection.getter.GetterPropertyResolver;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.persistence.metamodel.Attribute;
import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IntrospectionUtils {

  private static final Cache<Object, Function> GETTERS = CacheBuilder.newBuilder().build();

  @SneakyThrows
  public static <T, V> Function<T, V> asGetter(Attribute<T, V> attribute) {
    return GETTERS.get(
        asList(attribute.getDeclaringType().getJavaType(), attribute.getName()),
        () -> ReflectionUtils.asGetter(attribute.getJavaMember()));
  }

  @SneakyThrows
  public static Function asGetter(Class clazz, String field) {
    return GETTERS.get(
        asList(clazz, field),
        () -> ReflectionUtils.asGetter(clazz.getMethod("get" + capitalize(field))));
  }

  private static final Cache<Object, BiConsumer> SETTERS = CacheBuilder.newBuilder().build();

  @SneakyThrows
  public static <T, V> BiConsumer<T, V> asSetter(Attribute<T, V> attribute) {
    return asSetter(
        attribute.getDeclaringType().getJavaType(), attribute.getName(), attribute.getJavaType());
  }

  @SneakyThrows
  public static <T, V> BiConsumer<T, V> asSetter(
      Class<T> clazz, String propertyName, Class<V> propertyType) {
    return SETTERS.get(
        asList(clazz, propertyName),
        () ->
            ReflectionUtils.asSetter(
                clazz.getMethod("set" + capitalize(propertyName), propertyType)));
  }

  @SneakyThrows
  public static BiConsumer asSetter(Class clazz, String field) {
    return asSetter(clazz, field, resolveType(clazz, field));
  }

  private static final Cache<Object, GetterPropertyResolver> RESOLVERS =
      CacheBuilder.newBuilder().build();

  @SneakyThrows
  public static <T> GetterPropertyResolver<T> makeResolver(Class<T> clazz) {
    return RESOLVERS.get(clazz, () -> ReflectionUtils.makeGetterPropertyResolver(clazz));
  }

  private static final Cache<Object, Class> FIELD_TYPES = CacheBuilder.newBuilder().build();

  @SneakyThrows
  public static Class resolveType(Class clazz, String field) {
    return FIELD_TYPES.get(
        asList(clazz, field), () -> makeResolver(clazz).resolveType(asGetter(clazz, field)));
  }
}
