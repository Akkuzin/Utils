package aaa.lang.reflection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

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
	public void testGetTypeClassA0() throws Exception {
		assertNotSame(String.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 1));
	}

	@Test
	public void testGetTypeClassA1() throws Exception {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 0));
	}

	@Test
	public void testGetTypeClassA2() throws Exception {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, A.class, 1));
	}

	@Test
	public void testGetTypeClassB0() throws Exception {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 0));
	}

	@Test
	public void testGetTypeClassB1() throws Exception {
		assertEquals(String.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 1));
	}

	@Test
	public void testGetTypeClassB2() throws Exception {
		assertEquals(Set.class, ReflectionUtils.getGenericParameterClass(G.class, B.class, 2));
	}

	@Test
	public void testGetTypeClassF0() throws Exception {
		assertEquals(Byte.class, ReflectionUtils.getGenericParameterClass(G.class, F.class, 0));
	}

	@Test
	public void testGetTypeClassF1() throws Exception {
		assertEquals(Long.class, ReflectionUtils.getGenericParameterClass(G.class, F.class, 1));
	}

	@Test
	public void testGetTypeClassFromInterface() throws Exception {
		assertEquals(Integer.class, ReflectionUtils.getGenericParameterClass(G.class, LL.class, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeClassSelf() throws Exception {
		ReflectionUtils.getGenericParameterClass(A.class, A.class, 0);
	}

}
