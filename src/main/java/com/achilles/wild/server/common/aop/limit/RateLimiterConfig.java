package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimiterConfig {

    private static final Integer[] limits = {
            1,2,3,5,10,
            20,30,50,100,
            200,300,500,600,800,1000
    };

    private final static Map<Integer, RateLimiter> rateLimiterMap = new HashMap<>(limits.length);

    static {
        for (Integer limit:limits) {
            RateLimiter rateLimiter = RateLimiter.create(limit);
            rateLimiterMap.put(limit,rateLimiter);
        }
    }

    Object lock = new Object();

    public RateLimiter getRateLimiter(Integer limit){

        RateLimiter rateLimiter = rateLimiterMap.get(limit);
        if (rateLimiter != null) {
            return rateLimiter;
        }

        synchronized (lock) {
            rateLimiter = rateLimiterMap.get(limit);
            if (rateLimiter == null) {
                rateLimiter = RateLimiter.create(limit);
                rateLimiterMap.put(limit,rateLimiter);
            }
        }

        return rateLimiter;
    }

    @PostConstruct
    public void initRateLimiterMap(){

        Map<Integer, RateLimiter> rateLimiterMap = new HashMap<>(limits.length);
        for (Integer limit:limits) {
            RateLimiter rateLimiter = RateLimiter.create(limit);
            rateLimiterMap.put(limit,rateLimiter);
        }

    }
}
