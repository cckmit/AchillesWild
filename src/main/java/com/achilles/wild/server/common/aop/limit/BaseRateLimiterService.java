package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;

public interface BaseRateLimiterService {

    RateLimiter getRateLimiter(Double limit);

    Double getPermitsPerSecond();

    default RateLimiter getInstance(Map<Double, RateLimiter> rateLimiterMap, Double permitsPerSecond){

        RateLimiter rateLimiter = rateLimiterMap.get(permitsPerSecond);
        if (rateLimiter != null) {
            return rateLimiter;
        }

        synchronized (this) {
            rateLimiter = rateLimiterMap.get(permitsPerSecond);
            if (rateLimiter == null) {
                rateLimiter = RateLimiter.create(permitsPerSecond);
                rateLimiterMap.put(permitsPerSecond,rateLimiter);
            }
        }

        return rateLimiter;
    }
}
