package com.heroxu.cases.configuration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
@PropertySource(value = {"classpath:test.properties"})
public class TestConfig {

    @Autowired
    private Environment env;

    public Map<String, Object> map=new HashMap<>();

    @Bean
    public Map<String, Object> test() {
        String testName = env.getProperty("test1.name");
        Integer testId = Integer.valueOf(env.getProperty("test1.id"));
        System.out.println("test1 -> | testName -> " + testName + " | test id -> " + testId);
        map.put("name", testName);
        map.put("id", testId);
        return map;
    }

}
