package com.achilles.wild.server.common.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogin {

    boolean value() default true;
}
