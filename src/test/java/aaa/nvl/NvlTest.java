/**
 * 
 */
package aaa.nvl;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class NvlTest {

	@SuppressWarnings("nls")
	@Test
	public void minTest() {
		assertEquals("", Nvl.min("123", ""));
		assertEquals("", Nvl.min("", "123"));
		assertEquals("123", Nvl.min("123", null));
		assertEquals("123", Nvl.min(null, "123"));
		assertEquals((Integer) 123, Nvl.min(null, 123));
		assertEquals((Integer) 123, Nvl.min(123, null));
		assertEquals((Integer) 123, Nvl.min(123, 123));
		assertEquals((Integer) (-123), Nvl.min(123, -123));
		assertEquals((Integer) (-123), Nvl.min(-123, 123));
		assertEquals(null, Nvl.min((Integer) null, null));
		assertEquals((Integer) (-321), Nvl.min(123, -321));
		assertEquals((Integer) 123, Nvl.min(123, 321));
		assertEquals((Integer) (-123), Nvl.min(-123, 321));
	}

	@SuppressWarnings("nls")
	@Test
	public void maxTest() {
		assertEquals("123", Nvl.max("123", ""));
		assertEquals("123", Nvl.max("", "123"));
		assertEquals("123", Nvl.max("123", null));
		assertEquals("123", Nvl.max(null, "123"));
		assertEquals((Integer) 123, Nvl.max(null, 123));
		assertEquals((Integer) 123, Nvl.max(123, null));
		assertEquals((Integer) 123, Nvl.max(123, 123));
		assertEquals((Integer) 123, Nvl.max(123, -123));
		assertEquals((Integer) 123, Nvl.max(-123, 123));
		assertEquals(null, Nvl.max((Integer) null, null));
		assertEquals((Integer) 123, Nvl.max(123, -321));
		assertEquals((Integer) 321, Nvl.max(123, 321));
		assertEquals((Integer) 321, Nvl.max(-123, 321));
	}

	@SuppressWarnings("nls")
	@Test
	public void nvlTest() {
		assertEquals("123", Nvl.nvl("123", "234"));
		assertEquals("234", Nvl.nvl(null, "234"));
		assertNull(Nvl.nvl(null, null));
	}

	@SuppressWarnings("nls")
	@Test
	public void coalesceTest() {
		assertEquals("123", Nvl.coalesce("123", "234", "567"));
		assertEquals("123", Nvl.coalesce("123", null, "567"));
		assertEquals("234", Nvl.coalesce(null, "234", null));
		assertEquals("567", Nvl.coalesce(null, null, "567"));
		assertEquals("567", Nvl.coalesce(null, null, "567", null));
		assertEquals("567", Nvl.coalesce(null, null, "567", "999"));
		assertEquals(Optional.empty(), Nvl.coalesce(null, null, null));
	}

	@SuppressWarnings("nls")
	@Test
	public void equalsTest() {
		assertEquals(true, Nvl.equals(null, null));
		assertEquals(false, Nvl.equals(1, null));
		assertEquals(false, Nvl.equals(null, 1));
		assertEquals(true, Nvl.equals(1, 1));
		assertEquals(true, Nvl.equals(100000, 100000));
		assertEquals(false, Nvl.equals(100000, null));
		assertEquals(false, Nvl.equals(null, 100000));
		assertEquals(false, Nvl.equals("qwe", null));
		assertEquals(false, Nvl.equals(null, "qwe"));
		assertEquals(true, Nvl.equals("qwe", "qwe"));
	}
}
