//package com.achilles.wild.server.common.aop.limit;
//
//import com.google.common.util.concurrent.RateLimiter;
//
//import java.util.Map;
//
//public abstract class BaseRateLimiterConfig {
//
//
//    public abstract RateLimiter getRateLimiter(Double limit);
//
//    public abstract Double getPermitsPerSecond();
//
//    protected RateLimiter getInstance(Map<Double, RateLimiter> rateLimiterMap,Double permitsPerSecond){
//
//        RateLimiter rateLimiter = rateLimiterMap.get(permitsPerSecond);
//        if (rateLimiter != null) {
//            return rateLimiter;
//        }
//
//        synchronized (this) {
//            rateLimiter = rateLimiterMap.get(permitsPerSecond);
//            if (rateLimiter == null) {
//                rateLimiter = RateLimiter.create(permitsPerSecond);
//                rateLimiterMap.put(permitsPerSecond,rateLimiter);
//            }
//        }
//
//        return rateLimiter;
//    }
//
//    //    protected  Map<Double, RateLimiter>  initRateLimiterMap(Double[] initPermitsPerSecond){
////
////        if (initPermitsPerSecond == null || initPermitsPerSecond.length == 0) {
////            throw new IllegalArgumentException("initLimits can not be null !");
////        }
////
////        Map<Double, RateLimiter>  rateLimiterMap = new HashMap<>(initPermitsPerSecond.length);
////
////        for (Double limit: initPermitsPerSecond) {
////            RateLimiter rateLimiter = RateLimiter.create(limit);
////            rateLimiterMap.put(limit,rateLimiter);
////        }
////
////        return rateLimiterMap;
////    }
//
//}
