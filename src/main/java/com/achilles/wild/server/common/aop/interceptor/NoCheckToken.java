package com.achilles.wild.server.common.aop.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NoCheckToken {

    boolean value() default true;
}
