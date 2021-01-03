package com.achilles.wild.server.cache.redis.base;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	
	/**
	 * 取得redis的客户端，可以执行命令了。
	 */
	ShardedJedis getShardedJedis();
	
	/**
	 * 将资源返还给pool
	 */
	void returnResource(ShardedJedis shardedJedis);

}
