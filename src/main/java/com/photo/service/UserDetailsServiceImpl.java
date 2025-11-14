package com.photo.service;

import com.photo.entity.User;
import com.photo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 用户详情服务实现
 * 用于Spring Security认证
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    /**
     * 最大失败尝试次数
     */
    private static final int MAX_FAILED_ATTEMPTS = 5;
    
    /**
     * 账户锁定时间(分钟)
     */
    private static final int LOCK_DURATION_MINUTES = 30;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("尝试加载用户: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
        
        // 检查账户是否被锁定
        if (user.getLocked()) {
            // 检查锁定时间是否已过
            if (user.getLockedTime() != null) {
                LocalDateTime unlockTime = user.getLockedTime().plusMinutes(LOCK_DURATION_MINUTES);
                if (LocalDateTime.now().isAfter(unlockTime)) {
                    // 锁定时间已过，自动解锁
                    user.unlock();
                    userRepository.save(user);
                    log.info("用户 {} 已自动解锁", username);
                } else {
                    log.warn("用户 {} 账户被锁定", username);
                    throw new RuntimeException("账户已被锁定，请稍后再试");
                }
            }
        }
        
        // 检查账户是否启用
        if (!user.getEnabled()) {
            log.warn("用户 {} 账户未启用", username);
            throw new RuntimeException("账户未启用");
        }
        
        log.debug("成功加载用户: {}", username);
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false)
                .accountLocked(user.getLocked())
                .credentialsExpired(false)
                .disabled(!user.getEnabled())
                .build();
    }
    
    /**
     * 登录成功处理
     */
    @Transactional
    public void loginSuccess(String username) {
        log.info("用户 {} 登录成功", username);
        userRepository.findByUsername(username).ifPresent(user -> {
            if (user.getFailedAttempts() > 0) {
                user.resetFailedAttempts();
                userRepository.save(user);
                log.debug("重置用户 {} 的失败尝试次数", username);
            }
        });
    }
    
    /**
     * 登录失败处理
     */
    @Transactional
    public void loginFailure(String username) {
        log.warn("用户 {} 登录失败", username);
        userRepository.findByUsername(username).ifPresent(user -> {
            user.incrementFailedAttempts();
            
            if (user.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
                user.lock();
                log.warn("用户 {} 连续失败{}次，账户已被锁定", username, MAX_FAILED_ATTEMPTS);
            }
            
            userRepository.save(user);
            log.debug("用户 {} 失败尝试次数: {}", username, user.getFailedAttempts());
        });
    }
}
