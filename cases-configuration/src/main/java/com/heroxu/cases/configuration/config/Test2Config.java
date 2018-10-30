package com.heroxu.cases.configuration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = {"classpath:test2.properties"})
public class Test2Config {

    @Value("${test2.name}")
    private String testName;

    @Value("${test2.id}")
    private Integer testId;

    public Map<String, Object> map2=new HashMap<>();

    @Bean
    public Map<String, Object> test2() {

        System.out.println("test2 -> | testName -> " + testName + " | test id -> " + testId);
        map2.put("name", testName);
        map2.put("id", testId);
        return map2;
    }
}
