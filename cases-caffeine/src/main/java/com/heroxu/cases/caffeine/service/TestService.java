package com.heroxu.cases.caffeine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @Cacheable(cacheNames = "TEST_A")
    public int testA(){
        log.info("testA not get from cache!");
        return 100;
    }

    @Cacheable(cacheNames = "TEST_B")
    public int testB(){
        log.info("testB not get from cache!");
        return 99;
    }

}
