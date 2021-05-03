package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimiterConfig extends BaseRateLimiterConfig{

    private static final Double[] initLimits = {
            0.1,0.2,0.5,
            1.0,2.0,3.0,5.0,10.0,
            20.0,30.0,50.0,100.0
    };

    private final static Map<Double, RateLimiter> rateLimiterMap = new HashMap<>(initLimits.length);

    static {
        for (Double limit : initLimits) {
            RateLimiter rateLimiter = RateLimiter.create(limit);
            rateLimiterMap.put(limit,rateLimiter);
        }
    }

    Object lock = new Object();

    public RateLimiter getRateLimiter(Double limit){

        RateLimiter rateLimiter = getInstance(rateLimiterMap,limit);


        return rateLimiter;
    }

//    @PostConstruct
//    public void initRateLimiterMap(){
//
//        Map<Double, RateLimiter> rateLimiterMap = new HashMap<>(initLimits.length);
//        for (Double limit: initLimits) {
//            RateLimiter rateLimiter = RateLimiter.create(limit);
//            rateLimiterMap.put(limit,rateLimiter);
//        }
//
//    }
}
