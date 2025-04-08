package com.metarash.backend.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int value() default 100;
    String key() default "global";
    TimeUnit timeUnit() default TimeUnit.MINUTES;
    long ttl() default 60; // TTL в секундах
}
