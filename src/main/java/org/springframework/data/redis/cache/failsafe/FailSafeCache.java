package org.springframework.data.redis.cache.failsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

public class FailSafeCache implements Cache {

	private static final Logger log = LoggerFactory.getLogger(FailSafeCache.class);
	
	private Cache underlyingCache;
	
	public FailSafeCache(Cache redisCache) {
		underlyingCache = redisCache;
	}

	public String getName() {
		return underlyingCache.getName();
	}

	public Object getNativeCache() {
		return underlyingCache.getNativeCache();
	}

	public ValueWrapper get(Object key) {
		ValueWrapper result = null;
		try {
			result = underlyingCache.get(key);
		} catch(Exception e) {
			log.error("Failed to get key: '{}' from cache.", key, e);
		}
		return result;
	}

	public void put(Object key, Object value) {
		try {
			underlyingCache.put(key, value);
		} catch(Exception e) {
			if(log.isErrorEnabled()) {
				log.error("Failed to put key/value pair: '{" + key + "," + value  + "}'.", e);
			}
		}
	}

	public void evict(Object key) {
		try {
			underlyingCache.evict(key);
		} catch(Exception e) {
			log.error("Failed to evict key: '{}'.", key, e);
		}
	}

	public void clear() {
		try {
			underlyingCache.clear();
		} catch(Exception e) {
			log.error("Failed to clear cache.", e);
		}
	}
}
