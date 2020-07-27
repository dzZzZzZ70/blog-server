package com.dz.blogserver.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {
    @Bean
    public DozerBeanMapperFactoryBean mapper() {
        return new DozerBeanMapperFactoryBean();
    }
}
