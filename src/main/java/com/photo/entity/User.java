package com.photo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于存储用户认证信息
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * 密码(BCrypt加密)
     */
    @Column(nullable = false, length = 100)
    private String password;
    
    /**
     * 账户是否启用
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;
    
    /**
     * 账户是否锁定
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean locked = false;
    
    /**
     * 连续失败登录次数
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer failedAttempts = 0;
    
    /**
     * 账户锁定时间
     */
    private LocalDateTime lockedTime;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * 增加失败尝试次数
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }
    
    /**
     * 重置失败尝试次数
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }
    
    /**
     * 锁定账户
     */
    public void lock() {
        this.locked = true;
        this.lockedTime = LocalDateTime.now();
    }
    
    /**
     * 解锁账户
     */
    public void unlock() {
        this.locked = false;
        this.lockedTime = null;
        this.failedAttempts = 0;
    }
}
