package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimiterConfig extends BaseRateLimiterConfig{

    @Value("${request.rate.limit:0.2}")
    Double permitsPerSecond;

//    @Value("#{'${request.rate.limit.init:0.1,0.2,0.5,2.0,3.0,10.0}'.split(',')}")
//    Double[] initPermitsPerSecond;

    private final Map<Double, RateLimiter> rateLimiterMap = new HashMap<>();


    @PostConstruct
    private void initRateLimiterMap(){
//        rateLimiterMap = initRateLimiterMap(initPermitsPerSecond);
        RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);
        rateLimiterMap.put(permitsPerSecond,rateLimiter);
    }

    @Override
    public Double getPermitsPerSecond() {
        return permitsPerSecond;
    }

    public RateLimiter getRateLimiter(Double limit){

        RateLimiter rateLimiter = getInstance(rateLimiterMap,limit);

        return rateLimiter;
    }


}
