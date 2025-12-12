package com.photo.config;

import com.photo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 * 应用启动时初始化默认数据
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    
    private final AuthService authService;
    
    @Override
    public void run(ApplicationArguments args) {
        log.info("开始初始化数据...");
        authService.initializeDefaultUsers();
        log.info("数据初始化完成");
    }
}
