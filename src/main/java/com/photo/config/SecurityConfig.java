package com.photo.config;

import com.photo.service.CustomRememberMeService;
import com.photo.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final SecurityProperties securityProperties;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomRememberMeService rememberMeService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 启用CSRF保护（登录表单需要CSRF Token）
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // H2控制台不需要CSRF
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 修改会话策略为按需创建（支持表单登录）
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            // 配置访问控制规则
            .authorizeHttpRequests(auth -> auth
                // 公开访问路径
                .requestMatchers(
                    "/login",           // 登录页面
                    "/css/**",          // 静态CSS资源
                    "/js/**",           // 静态JS资源
                    "/images/**",       // 静态图片资源
                    "/h2-console/**",   // H2数据库控制台
                    "/api-docs/**",     // API文档
                    "/swagger-ui/**",   // Swagger UI
                    "/swagger-ui.html", // Swagger UI首页
                    "/photos/view/**",  // 照片查看
                    "/photos/download/**", // 照片下载
                    "/photos/public/**"    // 公开照片
                ).permitAll()
                // 受保护的资源路径（需要认证）
                .requestMatchers(
                    "/index.html",      // 系统首页
                    "/notes.html",      // 笔记页面
                    "/api/notes/**",    // 笔记API
                    "/api/photos/**"    // 照片API
                ).authenticated()
                // 其他请求也需要认证
                .anyRequest().authenticated()
            )
            // 配置表单登录
            .formLogin(form -> form
                .loginPage("/login")              // 登录页面URL
                .loginProcessingUrl("/login")     // 登录处理URL
                .defaultSuccessUrl("/index.html", true) // 登录成功后跳转
                .failureUrl("/login?error")       // 登录失败后跳转
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll()
            )
            // 配置登出
            .logout(logout -> logout
                .logoutUrl("/logout")             // 登出URL
                .logoutSuccessUrl("/login?logout") // 登出成功后跳转
                .deleteCookies("JSESSIONID", "remember-me") // 删除Cookie
                .invalidateHttpSession(true)      // 使会话失效
                .permitAll()
            )
            // 配置记住我功能
            .rememberMe(remember -> remember
                .rememberMeServices(rememberMeService)
                .key("uniqueAndSecret")           // 加密密钥
                .tokenValiditySeconds(7 * 24 * 60 * 60) // Token有效期7天
            )
            // 配置异常处理
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    // 未认证时重定向到登录页
                    response.sendRedirect("/login");
                })
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        if (securityProperties.getCors().getEnabled()) {
            configuration.setAllowedOrigins(securityProperties.getCors().getAllowedOrigins());
            configuration.setAllowedMethods(securityProperties.getCors().getAllowedMethods());
            configuration.setAllowedHeaders(securityProperties.getCors().getAllowedHeaders());
            configuration.setAllowCredentials(securityProperties.getCors().getAllowCredentials());
        }
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置认证提供者
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    /**
     * 配置认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * 登录成功处理器
     */
    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            userDetailsService.loginSuccess(authentication.getName());
            response.sendRedirect("/index.html");
        };
    }
    
    /**
     * 登录失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String username = request.getParameter("username");
            if (username != null && !username.isEmpty()) {
                userDetailsService.loginFailure(username);
            }
            response.sendRedirect("/login?error");
        };
    }
}
