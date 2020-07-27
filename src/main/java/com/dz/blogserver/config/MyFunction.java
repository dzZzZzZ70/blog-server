package com.dz.blogserver.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})//可以定义在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFunction {
    String[] values();
}
