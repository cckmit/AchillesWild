package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.entity.user.User;
import com.achilles.wild.server.model.response.BaseResult;
import com.github.benmanes.caffeine.cache.Cache;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    private final static Logger log = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Autowired
    Cache caffeineCache;

    //@Autowired
    RedissonClient redissonClient;

    String key = "AchillesWild";

    private Integer m = 0;

    @GetMapping(path = "/redisson/set/{value}")
    public Object redissonLock(@PathVariable("value") Integer value){
        log.info("----param----val:"+value);

        RLock lock = redissonClient.getLock(key);
        lock.lock();
        m = m+value;
        log.info("----after----val:"+m);
        lock.unlock();
        return new BaseResult();
    }

    @GetMapping(path = "/redis/set/{value}")
    public Object redisSet(@PathVariable("value") String value){

        User user = new User();
        user.setEmail("wer3r");

        serializableRedisTemplate.opsForValue().set(key,user,20L, TimeUnit.SECONDS);
        Object val = serializableRedisTemplate.opsForValue().get(key);
        log.info("--------val:"+val);

        return val;
    }

    @GetMapping(path = "/caffeine/set/{value}")
    public Object caffeineSet(@PathVariable("value") String value){

        User user = new User();
        user.setEmail("wer3r");

        caffeineCache.put(key,user);
        Object val = caffeineCache.getIfPresent(key);
        log.info("--------val:"+val);

        return val;
    }
}
