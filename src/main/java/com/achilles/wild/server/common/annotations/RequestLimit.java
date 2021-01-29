package com.achilles.wild.server.common.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {


    int countLimit() default 1;

    int rateLimit() default 1;
}
