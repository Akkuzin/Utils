package aaa.basic;

import aaa.basis.LRUCache;
import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheTest {

	@SuppressWarnings("nls")
	@Test
	public void testOrderByGet() {
		LRUCache<Long, String> cache = new LRUCache<>(2);
		cache.put(1L, "Один");
		cache.put(2L, "Два");
		assertEquals(cache.get(2L), "Два");
		cache.put(3L, "Три");
		assertEquals(cache.get(1L), null);
	}

	@SuppressWarnings("nls")
	@Test
	public void testOrderByContainsKey() {
		LRUCache<Long, String> cache = new LRUCache<>(2);
		cache.put(1L, "Один");
		cache.put(2L, "Два");
		assertTrue(cache.containsKey(2L));
		cache.put(3L, "Три");
		assertEquals(cache.get(1L), null);
	}

	@SuppressWarnings("nls")
	@Test
	public void testOrderByContainsValue() {
		LRUCache<Long, String> cache = new LRUCache<>(2);
		cache.put(1L, "Один");
		cache.put(2L, "Два");
		assertTrue(cache.containsValue("Два"));
		cache.put(3L, "Три");
		assertEquals(cache.get(1L), null);
	}

}
