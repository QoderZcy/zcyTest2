package com.photo.controller;

import com.photo.dto.ApiResponse;
import com.photo.dto.LoginRequest;
import com.photo.dto.LoginResponse;
import com.photo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 认证控制器
 * 提供登录和登出接口
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * 用户登录
     * @param request 登录请求
     * @param httpRequest HTTP请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            LoginResponse response = authService.login(request);
            
            // 创建Session并存储用户信息
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("userId", response.getUserId());
            session.setAttribute("username", response.getUsername());
            
            // 设置Session超时时间
            if (request.getRememberMe() != null && request.getRememberMe()) {
                // 记住我: 7天
                session.setMaxInactiveInterval(7 * 24 * 60 * 60);
            } else {
                // 默认: 30分钟
                session.setMaxInactiveInterval(30 * 60);
            }
            
            log.info("用户登录成功,创建Session: {}, SessionId: {}", 
                    response.getUsername(), session.getId());
            
            return ApiResponse.success(response, "登录成功");
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return ApiResponse.error(401, e.getMessage());
        }
    }
    
    /**
     * 用户登出
     * @param httpRequest HTTP请求
     * @return 响应
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            session.invalidate();
            log.info("用户登出成功: {}", username);
            return ApiResponse.success("登出成功");
        }
        return ApiResponse.success("用户未登录");
    }
    
    /**
     * 检查登录状态
     * @param httpRequest HTTP请求
     * @return 用户信息
     */
    @GetMapping("/status")
    public ApiResponse<LoginResponse> checkLoginStatus(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            String username = (String) session.getAttribute("username");
            
            LoginResponse response = LoginResponse.builder()
                    .userId(userId)
                    .username(username)
                    .message("已登录")
                    .build();
            
            return ApiResponse.success(response, "已登录");
        }
        return ApiResponse.error(401, "未登录");
    }
}
