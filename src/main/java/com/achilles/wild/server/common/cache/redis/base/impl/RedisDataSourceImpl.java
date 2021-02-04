//package com.achilles.wild.server.common.cache.redis.base.impl;
//
//import com.achilles.wild.server.common.cache.redis.base.RedisDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//
//@Repository
//public class RedisDataSourceImpl implements RedisDataSource{
//
//	@Autowired
//	private ShardedJedisPool shardedJedisPool;
//
//
//
//
//	public Jedis getRedisClient1(String key) {
//		try{
//			Jedis shardJedis = shardedJedisPool.getResource().getShard(key);
//			return shardJedis;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public ShardedJedis getShardedJedis() {
//
//		ShardedJedis shardJedis = shardedJedisPool.getResource();
//
//		return shardJedis;
//	}
//
//	@Override
//	public void returnResource(ShardedJedis shardedJedis) {
//
//		if(shardedJedis!=null){
//			shardedJedis.close();
//		}
//
//	}
//
//
//}
