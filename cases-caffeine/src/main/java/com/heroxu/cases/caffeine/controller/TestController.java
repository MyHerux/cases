package com.heroxu.cases.caffeine.controller;

import com.heroxu.cases.caffeine.config.LocalCacheConfig;
import com.heroxu.cases.caffeine.service.TestService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private LocalCacheConfig localCacheConfig;

    @Resource
    private TestService testService;

    @GetMapping(value = "/test_a")
    public int getTestA(){
        return testService.testA();
    }

    @GetMapping(value = "/test_b")
    public int getTestB(){
        return testService.testB();
    }

    @GetMapping(value = "/test_c")
    public int getTestC(){
        return testService.testC();
    }

    @GetMapping(value = "/test_d")
    public int getTestD(){
        CacheManager cacheManager=localCacheConfig.MyCaffeineCacheManager();
        Cache cache = cacheManager.getCache("TEST_A");

        CaffeineCache caffeineCache= (CaffeineCache) cacheManager.getCache("TEST_A");
        long hitCount = caffeineCache.getNativeCache().stats().hitCount();
        long missCount = caffeineCache.getNativeCache().stats().missCount();
        double missRate = caffeineCache.getNativeCache().stats().missRate();
        log.error("hit count:{}",hitCount);
        log.error("miss count:{}",missCount);
        log.error("miss rate:{}",missRate);
        return testService.testA();
    }


}
