package com.photo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    /**
     * 配置Caffeine缓存管理器
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .recordStats());
        return cacheManager;
    }
    
    /**
     * 照片信息缓存
     */
    @Bean
    public Caffeine<Object, Object> photoCacheConfig() {
        return Caffeine.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .recordStats();
    }
    
    /**
     * 文件元数据缓存
     */
    @Bean
    public Caffeine<Object, Object> fileMetadataCacheConfig() {
        return Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .recordStats();
    }
    
    /**
     * 登录尝试缓存配置
     * 用于防暴力破解：记录失败次数，30分钟后自动过期（解锁）
     */
    @Bean
    public com.github.benmanes.caffeine.cache.Cache<String, Integer> loginAttemptCache() {
        return Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .recordStats()
            .build();
    }
}
