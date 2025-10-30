package com.photo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 安全配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    
    /**
     * 防盗链配置
     */
    private RefererConfig referer = new RefererConfig();
    
    /**
     * Token配置
     */
    private TokenConfig token = new TokenConfig();
    
    /**
     * CORS配置
     */
    private CorsConfig cors = new CorsConfig();
    
    @Data
    public static class RefererConfig {
        private Boolean enabled = true;
        private List<String> allowedDomains;
    }
    
    @Data
    public static class TokenConfig {
        private String secret = "your-secret-key";
        private Long expiration = 86400L; // 24小时
    }
    
    @Data
    public static class CorsConfig {
        private Boolean enabled = true;
        private List<String> allowedOrigins;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private Boolean allowCredentials = true;
    }
}
