package aaa.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import aaa.basis.LRUCache;
import org.junit.jupiter.api.Test;

public class LRUCacheTest {

  @Test
  public void testOrderByGet() {
    LRUCache<Long, String> cache = new LRUCache<>(2);
    cache.put(1L, "Один");
    cache.put(2L, "Два");
    assertEquals("Два", cache.get(2L));
    cache.put(3L, "Три");
    assertNull(cache.get(1L));
  }

  @Test
  public void testOrderByContainsKey() {
    LRUCache<Long, String> cache = new LRUCache<>(2);
    cache.put(1L, "Один");
    cache.put(2L, "Два");
    assertTrue(cache.containsKey(2L));
    cache.put(3L, "Три");
    assertNull(cache.get(1L));
  }

  @Test
  public void testOrderByContainsValue() {
    LRUCache<Long, String> cache = new LRUCache<>(2);
    cache.put(1L, "Один");
    cache.put(2L, "Два");
    assertTrue(cache.containsValue("Два"));
    cache.put(3L, "Три");
    assertNull(cache.get(1L));
  }
}
