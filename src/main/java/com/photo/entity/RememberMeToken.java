package com.photo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 记住我Token实体类
 * 用于存储持久化登录Token
 */
@Entity
@Table(name = "remember_me_tokens", indexes = {
    @Index(name = "idx_token", columnList = "token"),
    @Index(name = "idx_username", columnList = "username"),
    @Index(name = "idx_expires_at", columnList = "expiresAt")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RememberMeToken {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名
     */
    @Column(nullable = false, length = 50)
    private String username;
    
    /**
     * Token值(UUID生成)
     */
    @Column(nullable = false, unique = true, length = 100)
    private String token;
    
    /**
     * Token过期时间
     */
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    
    /**
     * Token创建时间
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 检查Token是否过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
