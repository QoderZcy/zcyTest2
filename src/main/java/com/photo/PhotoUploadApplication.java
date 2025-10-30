package com.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 照片上传下载系统主应用类
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class PhotoUploadApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PhotoUploadApplication.class, args);
    }
}
