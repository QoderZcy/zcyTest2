package com.photo.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录尝试管理服务
 * 实现防暴力破解机制：记录失败次数，5次后锁定30分钟
 */
@Service
public class LoginAttemptService {
    
    private static final int MAX_ATTEMPTS = 5;
    
    @Autowired
    private Cache<String, Integer> loginAttemptCache;
    
    /**
     * 生成缓存键：用户名+IP地址
     */
    private String getKey(String username, String ip) {
        return username + ":" + ip;
    }
    
    /**
     * 记录登录失败
     */
    public void incrementFailedAttempts(String username, String ip) {
        String key = getKey(username, ip);
        Integer attempts = loginAttemptCache.getIfPresent(key);
        if (attempts == null) {
            attempts = 0;
        }
        loginAttemptCache.put(key, attempts + 1);
    }
    
    /**
     * 检查账户是否被锁定
     */
    public boolean isBlocked(String username, String ip) {
        String key = getKey(username, ip);
        Integer attempts = loginAttemptCache.getIfPresent(key);
        return attempts != null && attempts >= MAX_ATTEMPTS;
    }
    
    /**
     * 获取剩余尝试次数
     */
    public int getRemainingAttempts(String username, String ip) {
        String key = getKey(username, ip);
        Integer attempts = loginAttemptCache.getIfPresent(key);
        if (attempts == null) {
            return MAX_ATTEMPTS;
        }
        return Math.max(0, MAX_ATTEMPTS - attempts);
    }
    
    /**
     * 获取当前失败次数
     */
    public int getFailedAttempts(String username, String ip) {
        String key = getKey(username, ip);
        Integer attempts = loginAttemptCache.getIfPresent(key);
        return attempts != null ? attempts : 0;
    }
    
    /**
     * 重置失败次数（登录成功后调用）
     */
    public void resetFailedAttempts(String username, String ip) {
        String key = getKey(username, ip);
        loginAttemptCache.invalidate(key);
    }
    
    /**
     * 获取最大尝试次数
     */
    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
}
