//package com.achilles.wild.server.cache.redis;
//
//import com.achilles.wild.server.cache.redis.base.RedisDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.exceptions.JedisException;
//
//@Repository
//public class RedisClient {
//
//	@Autowired
//	private RedisDataSource redisDataSource;
//
//
//	public String set(String key,int seconds,String value) {
//
//		ShardedJedis shardedJedis = redisDataSource.getShardedJedis();
//		if (shardedJedis == null) {
//			throw new JedisException("ShardedJedis is null");
//		}
//
//		String result = null;
//
//		try {
//			result = shardedJedis.setex(key,seconds,value);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			redisDataSource.returnResource(shardedJedis);
//		}
//
//		return result;
//	}
//
//	public String get(String key) {
//
//		ShardedJedis shardedJedis = redisDataSource.getShardedJedis();
//		if (shardedJedis == null) {
//			throw new JedisException("ShardedJedis is null");
//		}
//
//		String result = null;
//
//		try {
//			result = shardedJedis.get(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			redisDataSource.returnResource(shardedJedis);
//		}
//
//		return result;
//	}
//
//
//	public Long delete(String key) {
//
//		ShardedJedis shardedJedis = redisDataSource.getShardedJedis();
//		if (shardedJedis == null) {
//			throw new JedisException("ShardedJedis is null");
//		}
//
//		Long result = null;
//
//		try {
//			result = shardedJedis.del(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			redisDataSource.returnResource(shardedJedis);
//		}
//
//		return result;
//	}
//
//
//
//	public Boolean exists(String key) {
//
//		ShardedJedis shardedJedis = redisDataSource.getShardedJedis();
//		if (shardedJedis == null) {
//			throw new JedisException("ShardedJedis is null");
//		}
//
//		Boolean result =null;
//		try {
//			result = shardedJedis.exists(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			redisDataSource.returnResource(shardedJedis);
//		}
//		return result;
//	}
//
//
//
//
//
//
//}
