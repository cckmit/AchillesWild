package com.achilles.wild.server.common.aop.limit.annotation;

import com.achilles.wild.server.common.aop.limit.BaseRateLimiterConfig;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QpsLimit {

    double permitsPerSecond() default 1.0;

    Class<? extends BaseRateLimiterConfig> limitClass();


//    Class<?> limitClass() default RateLimiterConfig.class;
}
