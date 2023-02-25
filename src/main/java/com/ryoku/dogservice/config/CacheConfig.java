package com.ryoku.dogservice.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

    // jika tidak menggunakan ehcache, cara simplenya adalah dengan simple cache manager bean
//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        List<Cache> caches = new ArrayList<>();
//        caches.add(new ConcurrentMapCache("allBreedCache"));
//        caches.add(new ConcurrentMapCache("subBreedCache"));
//        cacheManager.setCaches(caches);
//        return cacheManager;
//    }
}
