package com.achilles.wild.server.cache.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRedisTemplate<K,V> {

//	@Resource(name="redisTemplate")
//	private RedisTemplate<String,String> stringRedisTemplate;
//	
//	@Resource(name="redisTemplate")
//	private RedisTemplate<String,Long> longRedisTemplate;
	
	@Resource(name="redisTemplate")
	private RedisTemplate<K,V> redisTemplate;
	
	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
	
	private ValueOperations<K, V> getValueOperations() {
		return redisTemplate.opsForValue();
	}
	
	private <HK, HV> HashOperations<K, HK, HV> getHashOperations() {
		return redisTemplate.opsForHash();
	}
	
	public void set2(K key,V value,long timeout) {
//		getHashOperations().put(key, hashKey, value);;;
//		stringRedisTemplate.
	}
	
	public void set(K key,V value,long timeout) {
		getValueOperations().set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	public V get(K key) {
		return getValueOperations().get(key);
	}
}
