package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;

public class BaseRateLimiterConfig {


    public RateLimiter getRateLimiter(Double limit){
        return null;
    }

    protected RateLimiter getInstance(Map<Double, RateLimiter> rateLimiterMap,Double limit){

        RateLimiter rateLimiter = rateLimiterMap.get(limit);
        if (rateLimiter != null) {
            return rateLimiter;
        }

        synchronized (this) {
            rateLimiter = rateLimiterMap.get(limit);
            if (rateLimiter == null) {
                rateLimiter = RateLimiter.create(limit);
                rateLimiterMap.put(limit,rateLimiter);
            }
        }

        return rateLimiter;
    }
}
