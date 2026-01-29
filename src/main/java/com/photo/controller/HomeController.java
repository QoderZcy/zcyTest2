package com.photo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器
 */
@Controller
public class HomeController {
    
    /**
     * 主页 - 如果未登录则会被安全配置重定向到登录页
     */
    @GetMapping("/")
    public String home() {
        return "dashboard"; // 已登录用户直接访问仪表板
    }
    
    /**
     * 仪表板页面（用户登录后）
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // 返回仪表板页面
    }
}