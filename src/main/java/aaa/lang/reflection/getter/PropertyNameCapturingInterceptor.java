package aaa.lang.reflection.getter;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class PropertyNameCapturingInterceptor {

	@RuntimeType
	public static Object intercept(@This aaa.lang.reflection.getter.PropertyNameCapturer capturer, @Origin Method method) {
		capturer.setPropertyName(getPropertyName(method));
		capturer.setPropertyType(method.getReturnType());
		if (method.getReturnType() == byte.class) {
			return (byte) 0;
		} else if (method.getReturnType() == short.class) {
			return (short) 0;
		} else if (method.getReturnType() == boolean.class) {
			return false;
		} else if (method.getReturnType() == int.class) {
			return 0;
		} else if (method.getReturnType() == long.class) {
			return 0L;
		} else if (method.getReturnType() == float.class) {
			return 0f;
		} else if (method.getReturnType() == double.class) {
			return 0d;
		} else {
			return null;
		}
	}

	private static String getPropertyName(Method method) {
		if (method.getParameterTypes().length != 0 || method.getReturnType() == null) {
			throw new IllegalArgumentException("Only property getter methods are expected to be passed");
		}
		return getPropertyName(method.getName());
	}

  public static String getPropertyName(String name) {
    if (name.startsWith("is")) {
      name = name.substring(2, 3).toLowerCase() + name.substring(3);
    } else if (name.startsWith("get") || name.startsWith("set")) {
      name = name.substring(3, 4).toLowerCase() + name.substring(4);
    }
    return name;
  }
}
