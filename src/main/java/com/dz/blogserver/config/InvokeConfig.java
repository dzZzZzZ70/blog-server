package com.dz.blogserver.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//@Configuration
public class InvokeConfig {
    private Map<String, Method> methodMap = new HashMap<>();

//    @Bean
//    public Map initMethod() {
//        Class clz = SettingService.class;
//        Method[] methods = clz.getDeclaredMethods();
//
//    }

//    @Bean
//    public Map getMethodMap() {
//        return initMethod();
//    }
}
