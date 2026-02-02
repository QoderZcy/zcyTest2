package com.photo.controller;

import com.photo.dto.ApiResponse;
import com.photo.service.CustomUserDetailsService;
import com.photo.service.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private SecurityContextRepository securityContextRepository;
    
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    /**
     * 显示登录页面
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * 处理登录请求
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiResponse<Map<String, Object>> login(
            @RequestParam String username, 
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response) {
        String ip = getClientIP(request);
        
        // 检查是否被锁定
        if (loginAttemptService.isBlocked(username, ip)) {
            Map<String, Object> data = new HashMap<>();
            data.put("locked", true);
            data.put("remainingAttempts", 0);
            return ApiResponse.error(429, "账户已被锁定30分钟，请稍后再试", data);
        }
        
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(username, password);
            
            // 执行认证
            Authentication authentication = authenticationManager.authenticate(authToken);
            
            // 设置安全上下文
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            
            // 更新最后登录时间
            userDetailsService.updateLastLogin(username);
            
            // 登录成功，重置失败计数器
            loginAttemptService.resetFailedAttempts(username, ip);
            
            Map<String, Object> result = new HashMap<>();
            result.put("message", "登录成功");
            result.put("username", username);
            result.put("redirectUrl", "/");
            
            return ApiResponse.success(result);
        } catch (Exception e) {
            // 记录登录失败
            loginAttemptService.incrementFailedAttempts(username, ip);
            int remaining = loginAttemptService.getRemainingAttempts(username, ip);
            
            Map<String, Object> data = new HashMap<>();
            data.put("locked", remaining == 0);
            data.put("remainingAttempts", remaining);
            
            if (remaining == 0) {
                return ApiResponse.error(429, "账户已被锁定30分钟，请稍后再试", data);
            }
            return ApiResponse.error(401, "用户名或密码错误，剩余" + remaining + "次尝试机会", data);
        }
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
    
    /**
     * 注销登录
     */
    @PostMapping("/logout")
    @ResponseBody
    public ApiResponse<String> logout() {
        SecurityContextHolder.clearContext();
        return ApiResponse.success("注销成功");
    }
    
    /**
     * 注册新用户
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiResponse<String> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String email) {
        try {
            userDetailsService.createUser(username, password, email);
            return ApiResponse.success("注册成功");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(500, "注册失败: " + e.getMessage());
        }
    }
}
