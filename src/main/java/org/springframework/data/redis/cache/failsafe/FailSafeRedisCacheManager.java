package org.springframework.data.redis.cache.failsafe;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class FailSafeRedisCacheManager extends RedisCacheManager {

	public FailSafeRedisCacheManager(RedisTemplate template) {
		super(template);
	}

	@Override
	protected Cache buildNewRedisCache(String name, long expiration) {
		Cache cache = super.buildNewRedisCache(name, expiration);
		return new FailSafeCache(cache);
	}
}
