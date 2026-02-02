package com.photo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photo.service.LoginAttemptService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录尝试拦截过滤器
 * 在认证前检查账户是否被锁定
 */
public class LoginAttemptFilter extends OncePerRequestFilter {
    
    private final LoginAttemptService loginAttemptService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public LoginAttemptFilter(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 仅对登录POST请求进行拦截
        if ("/auth/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");
            String ip = getClientIP(request);
            
            if (username != null && !username.isEmpty() && loginAttemptService.isBlocked(username, ip)) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(429); // Too Many Requests
                
                Map<String, Object> result = new HashMap<>();
                result.put("code", 429);
                result.put("message", "账户已被锁定30分钟，请稍后再试");
                
                Map<String, Object> data = new HashMap<>();
                data.put("locked", true);
                data.put("remainingAttempts", 0);
                result.put("data", data);
                
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            }
        }
        
        filterChain.doFilter(request, response);
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
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
