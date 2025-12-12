package com.photo.service;

import com.photo.dto.LoginRequest;
import com.photo.dto.LoginResponse;
import com.photo.entity.User;
import com.photo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 认证服务测试
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private AuthService authService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("$2a$10$encodedPassword")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .failedLoginAttempts(0)
                .createdAt(LocalDateTime.now())
                .build();
    }
    
    @Test
    void testLoginSuccess() {
        // 准备
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", testUser.getPassword()))
                .thenReturn(true);
        when(userRepository.save(any(User.class)))
                .thenReturn(testUser);
        
        // 执行
        LoginResponse response = authService.login(request);
        
        // 验证
        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals(1L, response.getUserId());
        assertEquals("登录成功", response.getMessage());
        
        // 验证失败次数被重置
        verify(userRepository).save(argThat(user -> 
            user.getFailedLoginAttempts() == 0 && 
            user.getLastLoginAt() != null
        ));
    }
    
    @Test
    void testLoginWithWrongPassword() {
        // 准备
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("wrongpassword")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", testUser.getPassword()))
                .thenReturn(false);
        
        // 执行 & 验证
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        
        assertEquals("用户名或密码错误", exception.getMessage());
        
        // 验证失败次数增加
        verify(userRepository).save(argThat(user -> 
            user.getFailedLoginAttempts() == 1
        ));
    }
    
    @Test
    void testLoginWithNonExistentUser() {
        // 准备
        LoginRequest request = LoginRequest.builder()
                .username("nonexistent")
                .password("password123")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("nonexistent"))
                .thenReturn(Optional.empty());
        
        // 执行 & 验证
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        
        assertEquals("用户名或密码错误", exception.getMessage());
    }
    
    @Test
    void testLoginWithDisabledAccount() {
        // 准备
        testUser.setEnabled(false);
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(testUser));
        
        // 执行 & 验证
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        
        assertEquals("账户已被禁用", exception.getMessage());
    }
    
    @Test
    void testLoginWithLockedAccount() {
        // 准备
        testUser.setLockedUntil(LocalDateTime.now().plusMinutes(10));
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(testUser));
        
        // 执行 & 验证
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        
        assertEquals("账户已被锁定,请稍后再试", exception.getMessage());
    }
    
    @Test
    void testAccountLockedAfterMaxFailedAttempts() {
        // 准备
        testUser.setFailedLoginAttempts(4); // 已经失败4次
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("wrongpassword")
                .rememberMe(false)
                .build();
        
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", testUser.getPassword()))
                .thenReturn(false);
        
        // 执行 & 验证
        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        
        // 验证账户被锁定
        verify(userRepository).save(argThat(user -> 
            user.getFailedLoginAttempts() == 5 &&
            user.getLockedUntil() != null
        ));
    }
    
    @Test
    void testInitializeDefaultUsers() {
        // 准备
        when(userRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        // 执行
        authService.initializeDefaultUsers();
        
        // 验证 - 应该创建2个用户(admin和user)
        verify(userRepository, times(2)).save(any(User.class));
    }
}
