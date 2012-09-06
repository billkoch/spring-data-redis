package org.springframework.data.redis.cache.failsafe;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.support.SimpleValueWrapper;

@RunWith(MockitoJUnitRunner.class)
public class FailSafeCacheTest {
	
	private FailSafeCache uut;

	@Mock
	private Cache mockCache;
	
	@Before
	public void setup() {
		uut = new FailSafeCache(mockCache);
	}
	
	@Test
	public void getShouldDelegateToUnderlyingCache() {
		ValueWrapper expectedValueWrapper = new SimpleValueWrapper(null);
		when(mockCache.get(anyObject())).thenReturn(expectedValueWrapper);
		
		ValueWrapper actualValueWrapper = uut.get(null);
		
		assertThat(actualValueWrapper, is(expectedValueWrapper));
		verify(mockCache).get(null);
	}
	
	@Test
	public void getShouldReturnNullIfUnderlyingCacheThrowsException() {
		when(mockCache.get(anyObject())).thenThrow(new RuntimeException("Simulated Exception"));
		
		ValueWrapper actualValueWrapper = uut.get(null);

		assertThat(actualValueWrapper, is(nullValue()));
		verify(mockCache).get(null);
	}
	
	@Test
	public void putShouldDelegateToUnderlyingRedisCache() {
		uut.put(null, null);
		
		verify(mockCache).put(null, null);
	}
	
	@Test
	public void putShouldFailGracefullyIfUnderlyingCacheThrowsException() {
		doThrow(new RuntimeException("Simulated Exception")).when(mockCache).put(null, null);
		
		uut.put(null, null);
		
		verify(mockCache).put(null, null);
	}
	
	@Test
	public void evictShouldDelegateToUnderlyingCache() {
		uut.evict(null);
		
		verify(mockCache).evict(null);
	}
	
	@Test
	public void evictShouldFailGracefullyIfUnderlyingCacheThrowsException() {
		doThrow(new RuntimeException("Simulated Exception")).when(mockCache).evict(null);
		
		uut.evict(null);
		
		verify(mockCache).evict(null);
	}
	
	@Test
	public void clearShouldDelegateToUnderlyingCache() {
		uut.clear();
		
		verify(mockCache).clear();
	}
	
	@Test
	public void clearShouldFailGracefullyIfUnderlyingCacheThrowsException() {
		doThrow(new RuntimeException("Simulated Exception")).when(mockCache).clear();
		
		uut.clear();
		
		verify(mockCache).clear();
	}
	
	@Test
	public void getNameShouldDelegateToUnderlyingCache() {
		uut.getName();
		
		verify(mockCache).getName();
	}
	
	@Test
	public void getNativeCacheShouldDelegateToUnderlyingCache() {
		uut.getNativeCache();
		
		verify(mockCache).getNativeCache();
	}
}
