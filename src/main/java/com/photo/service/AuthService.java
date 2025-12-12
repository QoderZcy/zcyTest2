package com.photo.service;

import com.photo.dto.LoginRequest;
import com.photo.dto.LoginResponse;
import com.photo.entity.User;
import com.photo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 认证服务
 * 处理用户登录、登出和会话管理
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 登录失败锁定阈值
     */
    private static final int MAX_FAILED_ATTEMPTS = 5;
    
    /**
     * 账户锁定时长(分钟)
     */
    private static final int LOCK_DURATION_MINUTES = 10;
    
    /**
     * 用户登录
     * @param request 登录请求
     * @return 登录响应
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("用户尝试登录: {}", request.getUsername());
        
        // 查找用户
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        
        // 检查账户是否被锁定
        if (user.getLockedUntil() != null && LocalDateTime.now().isBefore(user.getLockedUntil())) {
            log.warn("账户被锁定: {}", request.getUsername());
            throw new RuntimeException("账户已被锁定,请稍后再试");
        }
        
        // 检查账户是否启用
        if (!user.getEnabled()) {
            log.warn("账户已禁用: {}", request.getUsername());
            throw new RuntimeException("账户已被禁用");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("密码错误: {}", request.getUsername());
            handleFailedLogin(user);
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 登录成功,重置失败次数和锁定状态
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        
        log.info("用户登录成功: {}", request.getUsername());
        
        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token("SESSION") // 使用Session认证,不需要JWT token
                .message("登录成功")
                .build();
    }
    
    /**
     * 处理登录失败
     * @param user 用户
     */
    private void handleFailedLogin(User user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);
        
        // 达到失败次数阈值,锁定账户
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES));
            log.warn("账户因多次登录失败被锁定: {}, 锁定至: {}", 
                    user.getUsername(), user.getLockedUntil());
        }
        
        userRepository.save(user);
    }
    
    /**
     * 初始化默认用户
     * 用于测试
     */
    @Transactional
    public void initializeDefaultUsers() {
        if (userRepository.count() == 0) {
            log.info("初始化默认用户数据...");
            
            // 创建管理员用户
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .failedLoginAttempts(0)
                    .build();
            userRepository.save(admin);
            
            // 创建普通用户
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .failedLoginAttempts(0)
                    .build();
            userRepository.save(user);
            
            log.info("默认用户初始化完成");
        }
    }
}
