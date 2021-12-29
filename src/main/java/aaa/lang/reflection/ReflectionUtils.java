package aaa.lang.reflection;

import aaa.lang.reflection.getter.GetterPropertyResolver;
import aaa.lang.reflection.getter.PropertyNameCapturingInterceptor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.*;
import java.util.Optional;
import java.util.Stack;
import java.util.function.*;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class ReflectionUtils {

	/**
	 * Для некоторого класса (или интерфейса) определяет, каким классом был
	 * параметризован один из его предков (реализующих классов) с generic-параметрами.
	 *
	 * @param actualClass    анализируемый класс
	 * @param genericClass   класс (или интерфейс), для которого определяется значение параметра
	 * @param parameterIndex номер параметра
	 * @return класс, являющийся параметром с индексом parameterIndex в genericClass
	 */
	@SuppressWarnings("nls")
	public static Class<?> getGenericParameterClass(final Class<?> actualClass,
	                                                final Class<?> genericClass,
	                                                final int parameterIndex) {
		// Прекращаем работу, если genericClass не является предком actualClass.
		if (!genericClass.isAssignableFrom(actualClass) || genericClass.equals(actualClass)) {
			throw new IllegalArgumentException("Class " + genericClass.getName()
					+ " is not a superclass of " + actualClass.getName() + ".");
		}
		final boolean isInterface = genericClass.isInterface();

		// Нам нужно найти класс, для которого непосредственным родителем будет genericClass.
		// Мы будем подниматься вверх по иерархии, пока не найдем интересующий нас класс.
		// В процессе поднятия мы будем сохранять в genericClasses все классы - они нам понадобятся при спуске вниз.

		// Пройденные классы - используются для спуска вниз.
		Stack<ParameterizedType> genericClasses = new Stack<>();

		// clazz - текущий рассматриваемый класс
		Class<?> clazz = actualClass;

		while (true) {
			Type genericInterface = isInterface ? getGenericInterface(clazz, genericClass) : null;
			Type currentType =
					genericInterface != null ? genericInterface : clazz.getGenericSuperclass();

			boolean isParameterizedType = currentType instanceof ParameterizedType;
			if (isParameterizedType) {
				// Если предок - параметризованный класс, то запоминаем его - возможно он пригодится при спуске вниз.
				genericClasses.push((ParameterizedType) currentType);
			} else {
				// В иерархии встретился непараметризованный класс. Все ранее сохраненные параметризованные классы будут бесполезны.
				genericClasses.clear();
			}
			// Проверяем, дошли мы до нужного предка или нет.
			Type rawType =
					isParameterizedType ? ((ParameterizedType) currentType).getRawType()
							: currentType;
			if (!rawType.equals(genericClass)) {
				// genericClass не является непосредственным родителем для текущего класса. Поднимаемся по иерархии дальше.
				clazz = (Class<?>) rawType;
			} else {
				// Мы поднялись до нужного класса. Останавливаемся.
				break;
			}
		}

		// Нужный класс найден. Теперь мы можем узнать, какими типами он параметризован.
		Type result = genericClasses.pop().getActualTypeArguments()[parameterIndex];

		while (result instanceof TypeVariable<?> && !genericClasses.empty()) {
			// Похоже наш параметр задан где-то ниже по иерархии, спускаемся вниз.

			// Получаем индекс параметра в том классе, в котором он задан.
			int actualArgumentIndex = getParameterTypeDeclarationIndex((TypeVariable<?>) result);
			// Берем соответствующий класс, содержащий метаинформацию о нашем параметре.
			ParameterizedType type = genericClasses.pop();
			// Получаем информацию о значении параметра.
			result = type.getActualTypeArguments()[actualArgumentIndex];
		}

		if (result instanceof TypeVariable<?>) {
			// Мы спустились до самого низа, но даже там нужный параметр не имеет явного задания.
			// Следовательно из-за "Type erasure" узнать класс для параметра невозможно.
			throw new IllegalStateException("Unable to resolve type variable "
					+ result
					+ "."
					+ " Try to replace instances of parametrized class with its non-parameterized subtype.");
		}

		if (result instanceof ParameterizedType) {
			// Сам параметр оказался параметризованным. Отбросим информацию о его параметрах, она нам не нужна.
			result = ((ParameterizedType) result).getRawType();
		}

		if (result == null) {
			// Should never happen. :)
			throw new IllegalStateException("Unable to determine actual parameter type for "
					+ actualClass.getName() + ".");
		}

		if (!(result instanceof Class<?>)) {
			// Похоже, что параметр - массив, примитивный типи, интерфейс или еще-что-то, что не является классом.
			throw new IllegalStateException("Actual parameter type for " + actualClass.getName()
					+ " is not a Class.");
		}

		return (Class<?>) result;
	}

	@SuppressWarnings("nls")
	public static int getParameterTypeDeclarationIndex(final TypeVariable<?> typeVariable) {
		GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();

		// Ищем наш параметр среди всех параметров того класса, где определен нужный нам параметр
		TypeVariable<?>[] typeVariables = genericDeclaration.getTypeParameters();
		for (int i = 0; i < typeVariables.length; i++) {
			if (typeVariables[i].equals(typeVariable)) {
				return i;
			}
		}
		throw new IllegalStateException("Argument " + typeVariable.toString() + " is not found in "
				+ genericDeclaration.toString() + ".");
	}

	public static Type getGenericInterface(final Class<?> sourceClass,
	                                       final Class<?> genericInterface) {
		for (Type type : sourceClass.getGenericInterfaces()) {
			if (type instanceof Class<?>) {
				if (genericInterface.isAssignableFrom((Class<?>) type)) {
					return type;
				}
			} else if (type instanceof ParameterizedType) {
				if (genericInterface.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType())) {
					return type;
				}
			}
		}
		return null;
	}

	public static <T> GetterPropertyResolver<T> makeGetterPropertyResolver(Class<T> clazz) {
		return new GetterPropertyResolver(clazz);
	}

	private static final Cache<Method, Function> GETTERS = CacheBuilder.newBuilder()
			.weakValues()
			.build();

	private static final Cache<Method, BiConsumer> SETTERS = CacheBuilder.newBuilder()
			.weakValues()
			.build();

	@SneakyThrows
	private static Function createGetter(MethodHandles.Lookup lookup, MethodHandle getter) {
		return (Function) LambdaMetafactory.metafactory(lookup,
				"apply",
				MethodType.methodType(Function.class),
				MethodType.methodType(Object.class,
						Object.class), // Function.apply with erasure
				getter,
				getter.type())
				.getTarget()
				.invokeExact();
	}

	@SneakyThrows
	public static BiConsumer createSetter(MethodHandles.Lookup lookup,
	                                      MethodHandle setter) {
		Class<?> valueType = setter.type().parameterType(1);
		if (valueType.isPrimitive()) {
			if (valueType == boolean.class) {
				ObjBooleanConsumer consumer = (ObjBooleanConsumer) createSetterCallSite(
						lookup, setter, ObjBooleanConsumer.class, boolean.class).getTarget().invokeExact();
				return (a, b) -> consumer.accept(a, (boolean) b);
			} else if (valueType == long.class) {
				ObjLongConsumer consumer = (ObjLongConsumer) createSetterCallSite(
						lookup, setter, ObjLongConsumer.class, long.class).getTarget().invokeExact();
				return (a, b) -> consumer.accept(a, (long) b);
			} else if (valueType == double.class) {
				ObjDoubleConsumer consumer = (ObjDoubleConsumer) createSetterCallSite(
						lookup, setter, ObjDoubleConsumer.class, double.class).getTarget().invokeExact();
				return (a, b) -> consumer.accept(a, (double) b);
			} else if (valueType == int.class) {
				ObjIntConsumer consumer = (ObjIntConsumer) createSetterCallSite(
						lookup, setter, ObjIntConsumer.class, int.class).getTarget().invokeExact();
				return (a, b) -> consumer.accept(a, (int) b);
			} else {
				throw new RuntimeException("Type is not supported yet: " + valueType.getName());
			}
		} else {
			return (BiConsumer) createSetterCallSite(lookup, setter, BiConsumer.class, Object.class)
					.getTarget().invokeExact();
		}
	}

	@SneakyThrows
	private static CallSite createSetterCallSite(MethodHandles.Lookup lookup,
	                                             MethodHandle setter,
	                                             Class<?> interfaceType,
	                                             Class<?> valueType) {
		return LambdaMetafactory.metafactory(lookup,
				"accept",
				MethodType.methodType(interfaceType),
				MethodType.methodType(void.class, Object.class, valueType),
				setter,
				setter.type());
	}

	@SneakyThrows
	public static Function reflectGetter(MethodHandles.Lookup lookup, Method getter) {
		return GETTERS.get(getter, () -> createGetter(lookup, lookup.unreflect(getter)));
	}

	@SneakyThrows
	public static BiConsumer reflectSetter(MethodHandles.Lookup lookup, Method setter) {
		return SETTERS.get(setter, () -> createSetter(lookup, lookup.unreflect(setter)));
	}

	@SneakyThrows
	public static <T, V> Function<T, V> asGetter(Member member) {
		if (member instanceof Method || member instanceof Field) {
			Lookup lookup = MethodHandles.lookup();
			// this should be a getter method:
			if (member instanceof Method) {
				return (Function<T, V>) reflectGetter(lookup, (Method) member);
			}
			String name = capitalize(member.getName());
			Field field = (Field) member;
			try {
				return (Function<T, V>) reflectGetter(lookup,
						field.getDeclaringClass().getMethod("get"
								+ name));
			} catch (NoSuchMethodException e) {
				return (Function<T, V>) reflectGetter(lookup,
						field.getDeclaringClass().getMethod("is"
								+ name));
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public static <T, V> Function<T, V> asGetter(Method method) {
		return (Function<T, V>) reflectGetter(MethodHandles.lookup(), method);
	}

	@SneakyThrows
	public static <T, V> BiConsumer<T, V> asSetter(Member member) {
		if (member instanceof Method || member instanceof Field) {
			Lookup lookup = MethodHandles.lookup();
			if (member instanceof Method) {
				return (BiConsumer<T, V>) reflectSetter(lookup, (Method) member);
			}
			return (BiConsumer<T, V>) reflectSetter(lookup, ((Field) member).getDeclaringClass()
					.getMethod("set" + capitalize(member.getName()), ((Field) member).getType()));
		} else {
			throw new UnsupportedOperationException();
		}
	}

  private static final Cache<SerializableBiConsumer, String> SETTERS_TO_PROPERTIES =
      CacheBuilder.newBuilder().weakValues().build();

  private static final Cache<SerializableFunction, String> GETTERS_TO_PROPERTIES =
      CacheBuilder.newBuilder().weakValues().build();

  @SneakyThrows
  public static <T, Q> String propertyNameFor(SerializableBiConsumer<T, Q> accessor) {
    return SETTERS_TO_PROPERTIES.get(accessor, () -> accessorToName(accessor));
  }

  @SneakyThrows
  public static <T, Q> String propertyNameFor(SerializableFunction<T, Q> accessor) {
    return GETTERS_TO_PROPERTIES.get(accessor, () -> accessorToName(accessor));
  }

  public interface SerializableBiConsumer<A, B> extends BiConsumer<A, B>, Serializable {}

  public interface SerializableFunction<A, B> extends Function<A, B>, Serializable {}

  private static String accessorToName(Serializable accessor) {
    return Optional.ofNullable(name(accessor))
        .map(PropertyNameCapturingInterceptor::getPropertyName)
        .orElse(null);
  }

  private static String name(Serializable accessor) {
    for (Class<?> clazz = accessor.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
      try {
        Method writeReplace = clazz.getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        Object replacement = writeReplace.invoke(accessor);
        if (!(replacement instanceof SerializedLambda)) {
          break; // custom interface implementation
        }
        return ((SerializedLambda) replacement).getImplMethodName();
      } catch (NoSuchMethodException e) {
      } catch (IllegalAccessException | InvocationTargetException e) {
        break;
      }
    }
    return null;
  }
}
