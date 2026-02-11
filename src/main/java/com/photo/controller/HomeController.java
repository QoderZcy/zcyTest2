package com.photo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器
 * 取消登录后，直接对外提供仪表盘页面
 */
@Controller
public class HomeController {
    
    /**
     * 主页
     */
    @GetMapping("/")
    public String home() {
        return "dashboard";
    }
    
    /**
     * 仪表板页面
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}