package com.photo.service;

import com.photo.entity.RememberMeToken;
import com.photo.repository.RememberMeTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * 自定义记住我服务
 * 实现基于数据库的持久化Token存储
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomRememberMeService extends AbstractRememberMeServices {
    
    private final RememberMeTokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    
    /**
     * Token有效期(秒) - 7天
     */
    private static final int TOKEN_VALIDITY_SECONDS = 7 * 24 * 60 * 60;
    
    /**
     * Cookie名称
     */
    private static final String COOKIE_NAME = "remember-me";
    
    public CustomRememberMeService(
            RememberMeTokenRepository tokenRepository,
            UserDetailsService userDetailsService) {
        super(COOKIE_NAME, userDetailsService);
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
        setTokenValiditySeconds(TOKEN_VALIDITY_SECONDS);
    }
    
    @Override
    @Transactional
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
                                   Authentication successfulAuthentication) {
        String username = successfulAuthentication.getName();
        log.info("为用户 {} 创建记住我Token", username);
        
        // 删除该用户的旧Token
        tokenRepository.deleteByUsername(username);
        
        // 生成新Token
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(TOKEN_VALIDITY_SECONDS);
        
        RememberMeToken token = RememberMeToken.builder()
                .username(username)
                .token(tokenValue)
                .expiresAt(expiresAt)
                .build();
        
        tokenRepository.save(token);
        
        // 设置Cookie
        Cookie cookie = new Cookie(COOKIE_NAME, tokenValue);
        cookie.setMaxAge(TOKEN_VALIDITY_SECONDS);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // 生产环境应启用HTTPS
        
        response.addCookie(cookie);
        log.debug("记住我Token已创建: {}", tokenValue);
    }
    
    @Override
    @Transactional
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
                                                  HttpServletResponse response) {
        if (cookieTokens.length != 1) {
            throw new InvalidCookieException("Cookie token 格式错误");
        }
        
        String tokenValue = cookieTokens[0];
        log.debug("处理自动登录Token: {}", tokenValue);
        
        RememberMeToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RememberMeAuthenticationException("Token不存在"));
        
        // 检查Token是否过期
        if (token.isExpired()) {
            log.warn("Token已过期: {}", tokenValue);
            tokenRepository.deleteByToken(tokenValue);
            throw new RememberMeAuthenticationException("Token已过期");
        }
        
        // 刷新Token有效期
        token.setExpiresAt(LocalDateTime.now().plusSeconds(TOKEN_VALIDITY_SECONDS));
        tokenRepository.save(token);
        
        log.info("用户 {} 通过记住我Token自动登录", token.getUsername());
        
        return userDetailsService.loadUserByUsername(token.getUsername());
    }
    
    @Override
    protected String[] decodeCookie(String cookieValue) {
        return new String[]{cookieValue};
    }
    
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        super.logout(request, response, authentication);
        
        if (authentication != null) {
            String username = authentication.getName();
            log.info("用户 {} 登出，删除记住我Token", username);
            tokenRepository.deleteByUsername(username);
        }
        
        // 删除Cookie中的Token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies)
                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                    .forEach(cookie -> {
                        String tokenValue = cookie.getValue();
                        tokenRepository.deleteByToken(tokenValue);
                    });
        }
    }
    
    /**
     * 清理过期Token (定时任务调用)
     */
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("开始清理过期的记住我Token");
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("过期Token清理完成");
    }
}
