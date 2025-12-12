package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 登录请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    @Size(min = 3, max = 20, message = "用户名长度应为3-20个字符")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    @Size(min = 6, max = 20, message = "密码长度应为6-20个字符")
    private String password;
    
    /**
     * 是否记住我
     */
    @Builder.Default
    private Boolean rememberMe = false;
}
