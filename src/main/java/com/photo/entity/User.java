package com.photo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于身份验证和授权
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名(登录名)
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * 密码哈希值
     */
    @Column(nullable = false, length = 100)
    private String password;
    
    /**
     * 账户状态
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;
    
    /**
     * 账户是否过期
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonExpired = true;
    
    /**
     * 账户是否被锁定
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonLocked = true;
    
    /**
     * 凭证(密码)是否过期
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    
    /**
     * 创建时间
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 最后登录时间
     */
    @Column
    private LocalDateTime lastLoginAt;
    
    /**
     * 登录失败次数
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;
    
    /**
     * 账户锁定时间
     */
    @Column
    private LocalDateTime lockedUntil;
    
    /**
     * 持久化前设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
