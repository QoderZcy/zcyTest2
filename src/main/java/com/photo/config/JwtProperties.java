package com.photo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置属性
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    
    /**
     * JWT密钥
     */
    private String secret = "MySecretKeyForJWTTokenGenerationAndValidation2024PhotoUploadSystem";
    
    /**
     * Token过期时间(毫秒) - 默认7天
     */
    private Long expiration = 604800000L;
    
    /**
     * 记住我过期时间(毫秒) - 默认30天
     */
    private Long rememberMeExpiration = 2592000000L;
    
    /**
     * Token前缀
     */
    private String tokenPrefix = "Bearer ";
    
    /**
     * Token请求头名称
     */
    private String headerName = "Authorization";
}
