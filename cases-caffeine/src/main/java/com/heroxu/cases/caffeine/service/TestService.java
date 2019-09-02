package com.heroxu.cases.caffeine.service;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.heroxu.cases.caffeine.config.LocalCacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
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

    @Cacheable(cacheNames = "TEST_A")
    public int testC(){
        log.info("testC not get from cache!");
        return 99;
    }

}
