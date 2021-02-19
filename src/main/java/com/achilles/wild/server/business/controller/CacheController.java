package com.achilles.wild.server.business.controller;

import com.achilles.wild.server.business.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/cache",produces = {"application/json;charset=UTF-8"})
public class CacheController {

    private final static Logger log = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    RedisTemplate redisTemplate;

    String key = "AchillesWild";

    @GetMapping(path = "/redis/set/{value}")
    public Object set(@PathVariable("value") String value){

        User user = new User();
        user.setEmail("wer3r");

        redisTemplate.opsForValue().set(key,user,20L, TimeUnit.SECONDS);
        Object val = redisTemplate.opsForValue().get(key);
        log.info("--------val:"+val);

        return val;
    }
}
