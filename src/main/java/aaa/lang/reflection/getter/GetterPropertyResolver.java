package aaa.lang.reflection.getter;

import aaa.threadlocal.NotSettableThreadLocal;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class GetterPropertyResolver<T> {

	private final ThreadLocal<T> holder;

	public static <T> T getPropertyNameCapturer(Class<T> type) {
		DynamicType.Builder<?> builder =
				new ByteBuddy().subclass(type.isInterface() ? Object.class : type);
		if (type.isInterface()) {
			builder = builder.implement(type);
		}
		Class<?> proxyType =
				builder.implement(aaa.lang.reflection.getter.PropertyNameCapturer.class)
						.defineField("propertyName", String.class, Visibility.PRIVATE)
						.defineField("propertyType", Class.class, Visibility.PRIVATE)
						.method(ElementMatchers.any())
						.intercept(MethodDelegation.to(aaa.lang.reflection.getter.PropertyNameCapturingInterceptor.class))
						.method(named("setPropertyName").or(named("getPropertyName")))
						.intercept(FieldAccessor.ofBeanProperty())
						.method(named("setPropertyType").or(named("getPropertyType")))
						.intercept(FieldAccessor.ofBeanProperty())
						.make()
						.load(aaa.lang.reflection.getter.PropertyNameCapturer.class.getClassLoader(),
								ClassLoadingStrategy.Default.WRAPPER)
						.getLoaded();
		try {
			@SuppressWarnings("unchecked")
			Class<T> typed = (Class<T>) proxyType;
			return typed.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new IllegalArgumentException("Couldn't instantiate proxy for retrieval of method name ", e);
		}
	}

	public GetterPropertyResolver(Class<T> clazz) {
		holder = NotSettableThreadLocal.withInitial(() -> getPropertyNameCapturer(clazz));
	}

	public <V> String resolveName(Function<T, V> getter) {
		T capturer = holder.get();
		getter.apply(capturer);
		return ((aaa.lang.reflection.getter.PropertyNameCapturer) capturer).getPropertyName();
	}

	public <V> Class<V> resolveType(Function<T, V> getter) {
		T capturer = holder.get();
		getter.apply(capturer);
		return ((aaa.lang.reflection.getter.PropertyNameCapturer) capturer).getPropertyType();
	}
}
