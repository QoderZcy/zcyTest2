package com.photo.config;

import com.photo.entity.User;
import com.photo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 */
@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 检查是否已有用户
            if (userRepository.count() == 0) {
                // 创建测试用户
                User admin = new User();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@example.com");
                admin.setEnabled(true);
                
                userRepository.save(admin);
                logger.info("创建默认管理员用户: admin (密码: 123456)");
                
                // 创建测试用户
                User testUser = new User();
                testUser.setUsername("test");
                testUser.setPassword(passwordEncoder.encode("123456"));
                testUser.setEmail("test@example.com");
                testUser.setEnabled(true);
                
                userRepository.save(testUser);
                logger.info("创建测试用户: test (密码: 123456)");
            } else {
                logger.info("用户数据已存在，跳过初始化");
            }
        };
    }
}
package com.photo.config;

import com.photo.entity.User;
import com.photo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 */
@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 检查是否已有用户
            if (userRepository.count() == 0) {
                // 创建测试用户
                User admin = new User();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@example.com");
                admin.setEnabled(true);
                
                userRepository.save(admin);
                logger.info("创建默认管理员用户: admin (密码: 123456)");
                
                // 创建测试用户
                User testUser = new User();
                testUser.setUsername("test");
                testUser.setPassword(passwordEncoder.encode("123456"));
                testUser.setEmail("test@example.com");
                testUser.setEnabled(true);
                
                userRepository.save(testUser);
                logger.info("创建测试用户: test (密码: 123456)");
            } else {
                logger.info("用户数据已存在，跳过初始化");
            }
        };
    }
}
package com.photo.config;

import com.photo.entity.User;
import com.photo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 */
@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 检查是否已有用户
            if (userRepository.count() == 0) {
                // 创建测试用户
                User admin = new User();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@example.com");
                admin.setEnabled(true);
                
                userRepository.save(admin);
                logger.info("创建默认管理员用户: admin (密码: 123456)");
                
                // 创建测试用户
                User testUser = new User();
                testUser.setUsername("test");
                testUser.setPassword(passwordEncoder.encode("123456"));
                testUser.setEmail("test@example.com");
                testUser.setEnabled(true);
                
                userRepository.save(testUser);
                logger.info("创建测试用户: test (密码: 123456)");
            } else {
                logger.info("用户数据已存在，跳过初始化");
            }
        };
    }
}
package com.photo.config;

import com.photo.entity.User;
import com.photo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 */
@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 检查是否已有用户
            if (userRepository.count() == 0) {
                // 创建测试用户
                User admin = new User();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@example.com");
                admin.setEnabled(true);
                
                userRepository.save(admin);
                logger.info("创建默认管理员用户: admin (密码: 123456)");
                
                // 创建测试用户
                User testUser = new User();
                testUser.setUsername("test");
                testUser.setPassword(passwordEncoder.encode("123456"));
                testUser.setEmail("test@example.com");
                testUser.setEnabled(true);
                
                userRepository.save(testUser);
                logger.info("创建测试用户: test (密码: 123456)");
            } else {
                logger.info("用户数据已存在，跳过初始化");
            }
        };
    }
}
