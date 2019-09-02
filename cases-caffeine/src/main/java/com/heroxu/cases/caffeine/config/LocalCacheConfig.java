package com.heroxu.cases.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class LocalCacheConfig {

    private static final int DEFAULT_MAXSIZE = 1000;
    private static final int DEFAULT_TTL = 1;

    /**
     * 定义不同的cache名称、超时市场（秒）、最大容量。
     *
     * 每个cache缺省：1秒超时、最多缓存1000条数据，需要修改可以在构造方法的参数中指定。
     */
    public enum Caches {
        TEST_A(10, 10),
        TEST_B(10 * 6, 100);


        Caches() {
        }

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        private int maxSize = DEFAULT_MAXSIZE;    //最大数量
        private int ttl = DEFAULT_TTL;        //过期时间（秒）

        public int getMaxSize() {
            return maxSize;
        }

        public int getTtl() {
            return ttl;
        }
    }

    /**
     * 创建基于Caffeine的Cache Manager
     */
    @Bean
    @Primary
    public CacheManager MyCaffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = new ArrayList<>();
        for (Caches c : Caches.values()) {
            caches.add(new CaffeineCache(c.name(),
                Caffeine.newBuilder().recordStats()
                    .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                    .maximumSize(c.getMaxSize())
                    .build())
            );
        }

        cacheManager.setCaches(caches);

        return cacheManager;
    }

}
