//package com.achilles.wild.server.common.cache.redis;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.achilles.wild.server.tool.SpringbootApplicationTests;
//import com.achilles.wild.server.common.cache.redis.BaseRedisTemplate;
//import com.achilles.wild.server.common.cache.redis.RedisClient;
//
//public class RedisTest extends SpringbootApplicationTests {
//	private final static Logger LOG = LoggerFactory.getLogger(RedisTest.class);
//
//	@Autowired
//	private RedisClient redisClient;
//
//	@Autowired
//	private BaseRedisTemplate<String,String> redisTemplate;
//
//
//
//	String key = "Achilles";
//
//	@Test
//	public void getSetTest() {
//		for(int m=0;m<20;m++) {
//			redisClient.set(key, 23,System.currentTimeMillis()+"");
//			LOG.info("redisClient-----------------"+m+"------------------"+redisClient.get(key));
//		}
//	}
//
////	@Test
////	public void getSetTest2() {
////		for(int m=0;m<20;m++) {
////			redisClient.hmset(key, hash);
////			LOG.info("redisClient-----------------"+m+"------------------"+redisClient.get(key));
////		}
////	}
//
//	@Test
//	public void getSetTimplatetTest() {
//		for(int m=0;m<3;m++) {
//			redisTemplate.set(key, System.currentTimeMillis()+"", 10);
//			LOG.info("redisTemplate-----------------"+m+"------------------"+redisTemplate.get(key));
//
////			listOps.set(key, index, value);
//		}
//	}
//}
