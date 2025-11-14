package com.photo.config;

import com.photo.repository.UserRepository;
import com.photo.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT认证过滤器
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private JwtProperties jwtProperties;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 从请求头中获取Token
            String bearerToken = request.getHeader(jwtProperties.getHeaderName());
            
            if (bearerToken != null && bearerToken.startsWith(jwtProperties.getTokenPrefix())) {
                String token = jwtUtils.extractToken(bearerToken);
                
                if (token != null) {
                    // 从Token中获取用户名
                    String username = jwtUtils.getUsernameFromToken(token);
                    
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        // 验证Token
                        if (jwtUtils.validateToken(token, username)) {
                            // 创建认证对象
                            UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(
                                    username, 
                                    null, 
                                    new ArrayList<>()
                                );
                            
                            authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            
                            // 设置到Spring Security上下文
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            
                            log.debug("JWT认证成功: {}", username);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("JWT认证失败: {}", e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }
}
