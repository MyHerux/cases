package com.heroxu.cases.configuration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Test3Config {

    @Value("${test3.name}")
    private String testName;

    @Value("${test3.id}")
    private Integer testId;

    public Map<String, Object> map3=new HashMap<>();

    @Bean
    public Map<String, Object> test2() {

        System.out.println("test3 -> | testName -> " + testName + " | test id -> " + testId);
        map3.put("name", testName);
        map3.put("id", testId);
        return map3;
    }
}
