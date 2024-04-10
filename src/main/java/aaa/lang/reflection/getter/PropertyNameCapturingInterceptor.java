package aaa.lang.reflection.getter;

import java.lang.reflect.Method;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class PropertyNameCapturingInterceptor {

  @RuntimeType
  public static Object intercept(
      @This aaa.lang.reflection.getter.PropertyNameCapturer capturer, @Origin Method method) {
    capturer.setPropertyName(getPropertyName(method));
    Class<?> returnType = method.getReturnType();
    capturer.setPropertyType(returnType);
    if (returnType == long.class) {
      return 0L;
    } else if (returnType == boolean.class) {
      return false;
    } else if (returnType == int.class) {
      return 0;
    } else if (returnType == double.class) {
      return 0d;
    } else if (returnType == float.class) {
      return 0f;
    } else if (returnType == byte.class) {
      return (byte) 0;
    } else if (returnType == short.class) {
      return (short) 0;
    } else {
      return null;
    }
  }

  private static String getPropertyName(Method method) {
    if (method.getParameterCount() != 0 || method.getReturnType() == null) {
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
