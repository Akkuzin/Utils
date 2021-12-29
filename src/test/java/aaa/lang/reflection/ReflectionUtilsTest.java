package aaa.lang.reflection;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static aaa.lang.reflection.ReflectionUtils.asGetter;
import static aaa.lang.reflection.ReflectionUtils.asSetter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ReflectionUtilsTest {

	private class Direct extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
	}

	@Test
	public void directTest() {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(Direct.class,
				ArrayList.class,
				0));
	}

	private class DirectSecondLevel extends ArrayList<ArrayList<String>> {
		private static final long serialVersionUID = 1L;
	}

	@Test
	public void directSecondLevelTest() {
		assertEquals(ArrayList.class, ReflectionUtils
				.getGenericParameterClass(DirectSecondLevel.class, ArrayList.class, 0));
	}

	private class DirectComplicated extends HashMap<ArrayList<String>, ArrayList<Integer>> {
		private static final long serialVersionUID = 1L;
	}

	@Test
	public void directComplicatedTest() {
		assertEquals(ArrayList.class, ReflectionUtils
				.getGenericParameterClass(DirectComplicated.class, HashMap.class, 0));
	}

	private class AA<T> {
	}

	private class BB extends AA<String> {
	}

	private class CC<T> extends BB {
	}

	private class DD extends CC<Object> {
	}

	@Test
	public void withSkippedTest() {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(DD.class, AA.class, 0));
	}

	static class A<K, L> {
		// String, Integer
	}

	static class B<P, Q, R extends Collection<?>> extends A<Q, P> {
		// Integer, String, Set
	}

	static class C<X extends Comparable<String>, Y, Z> extends B<Z, X, Set<Long>> {
		// String, Double, Integer
	}

	static class D<M, N extends Comparable<Double>> extends C<String, N, M> implements H<N, M> {
		// Integer, Double
	}

	static class E extends D<Integer, Double> {
		//
	}

	static class F<T, S> extends E {
		// Byte, Long
	}

	static class G extends F<Byte, Long> {
		//
	}

	interface H<H1, H2> extends LL<H2> {
		// Double, Integer
	}

	interface LL<L1> {
		// Integer
	}

	@Test
	public void testGetTypeClassA0() {
		assertNotSame(String.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 1));
	}

	@Test
	public void testGetTypeClassA1() {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 0));
	}

	@Test
	public void testGetTypeClassA2() {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 1));
	}

	@Test
	public void testGetTypeClassB0() {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 0));
	}

	@Test
	public void testGetTypeClassB1() {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 1));
	}

	@Test
	public void testGetTypeClassB2() {
		assertEquals(Set.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 2));
	}

	@Test
	public void testGetTypeClassF0() {
		assertEquals(Byte.class, ReflectionUtils.getGenericParameterClass(G.class, F.class, 0));
	}

	@Test
	public void testGetTypeClassF1() {
		assertEquals(Long.class, ReflectionUtils.getGenericParameterClass(G.class, F.class, 1));
	}

	@Test
	public void testGetTypeClassFromInterface() {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, LL.class, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeClassSelf() {
		ReflectionUtils.getGenericParameterClass(A.class, A.class, 0);
	}

	@Getter
	@Setter
	public static class ResultDto {
		double value;
		boolean check;
		Long count;
		String name;
	}

	@Test
	public void testSetterJava11() throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(ResultDto.class);
		Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
				.filter(p -> name.equals(p.getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Not found: " + name));
		PropertyDescriptor nameProperty = property.apply("name");
		BiConsumer nameSetter = asSetter(nameProperty.getWriteMethod());
		Function nameGetter = asGetter(nameProperty.getReadMethod());
		PropertyDescriptor valueProperty = property.apply("value");
		BiConsumer valueSetter = asSetter(valueProperty.getWriteMethod());
		Function valueGetter = asGetter(valueProperty.getReadMethod());
		PropertyDescriptor countProperty = property.apply("count");
		BiConsumer countSetter = asSetter(countProperty.getWriteMethod());
		Function countGetter = asGetter(countProperty.getReadMethod());
		PropertyDescriptor checkProperty = property.apply("check");
		BiConsumer checkSetter = asSetter(checkProperty.getWriteMethod());
		Function checkGetter = asGetter(checkProperty.getReadMethod());

		ResultDto dto = new ResultDto();

		assertEquals(null, nameGetter.apply(dto));
		assertEquals(null, countGetter.apply(dto));
		assertEquals(0d, (double) valueGetter.apply(dto), 0.001);
		assertEquals(false, checkGetter.apply(dto));

		nameSetter.accept(dto, "Position");
		countSetter.accept(dto, 42L);
		valueSetter.accept(dto, 123.45d);
		checkSetter.accept(dto, true);

		assertEquals("Position", nameGetter.apply(dto));
		assertEquals(42L, countGetter.apply(dto));
		assertEquals(123.45d, (double) valueGetter.apply(dto), 0.001);
		assertEquals(true, checkGetter.apply(dto));
	}

  @Test
  public void testPropertyNameFromGetter() {
    assertEquals("name", ReflectionUtils.propertyNameFor(ResultDto::getName));
    assertEquals("name", ReflectionUtils.propertyNameFor(ResultDto::setName));
    assertEquals("check", ReflectionUtils.propertyNameFor(ResultDto::isCheck));
    assertEquals("check", ReflectionUtils.propertyNameFor(ResultDto::setCheck));
  }
}
