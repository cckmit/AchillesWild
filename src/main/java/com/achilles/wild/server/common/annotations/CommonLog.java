package com.achilles.wild.server.common.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonLog {

    String value() default "";
}
