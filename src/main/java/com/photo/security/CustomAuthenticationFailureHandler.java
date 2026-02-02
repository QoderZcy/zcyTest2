package com.photo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photo.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义认证失败处理器
 * 记录登录失败次数，返回剩余尝试次数
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    private final LoginAttemptService loginAttemptService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public CustomAuthenticationFailureHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String ip = getClientIP(request);
        
        if (username != null && !username.isEmpty()) {
            loginAttemptService.incrementFailedAttempts(username, ip);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        
        if (loginAttemptService.isBlocked(username, ip)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            result.put("code", 403);
            result.put("message", "账户已被锁定30分钟，请稍后再试");
            data.put("locked", true);
            data.put("remainingAttempts", 0);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            int remaining = loginAttemptService.getRemainingAttempts(username, ip);
            result.put("code", 401);
            result.put("message", "用户名或密码错误，剩余" + remaining + "次尝试机会");
            data.put("locked", false);
            data.put("remainingAttempts", remaining);
        }
        
        result.put("data", data);
        response.getWriter().write(objectMapper.writeValueAsString(result));
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
        // 如果有多个IP，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
