package com.achilles.wild.server.common.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {


    int countLimit() default 100;

    int rateLimit() default 10;
}
