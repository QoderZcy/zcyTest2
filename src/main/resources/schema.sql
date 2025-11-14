-- 照片表初始化脚本(可选，JPA会自动创建表)
-- 如果需要手动创建表，可以使用以下SQL

-- MySQL版本
CREATE TABLE IF NOT EXISTS photos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_filename VARCHAR(500) NOT NULL COMMENT '原始文件名',
    stored_filename VARCHAR(100) NOT NULL UNIQUE COMMENT '存储文件名',
    file_path VARCHAR(1000) NOT NULL COMMENT '文件路径',
    thumbnail_path VARCHAR(1000) COMMENT '缩略图路径',
    file_size BIGINT NOT NULL COMMENT '文件大小(字节)',
    content_type VARCHAR(100) NOT NULL COMMENT '文件类型',
    extension VARCHAR(20) NOT NULL COMMENT '文件扩展名',
    width INT COMMENT '图片宽度',
    height INT COMMENT '图片高度',
    md5 VARCHAR(32) UNIQUE COMMENT 'MD5值',
    user_id VARCHAR(50) NOT NULL COMMENT '上传用户ID',
    access_count BIGINT DEFAULT 0 NOT NULL COMMENT '访问次数',
    download_count BIGINT DEFAULT 0 NOT NULL COMMENT '下载次数',
    is_public BOOLEAN DEFAULT TRUE NOT NULL COMMENT '是否公开',
    deleted BOOLEAN DEFAULT FALSE NOT NULL COMMENT '是否已删除',
    description VARCHAR(1000) COMMENT '描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    last_accessed_at TIMESTAMP COMMENT '最后访问时间',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    
    INDEX idx_original_filename (original_filename),
    INDEX idx_created_at (created_at),
    INDEX idx_user_id (user_id),
    INDEX idx_md5 (md5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='照片信息表';

-- 插入测试数据(可选)
-- INSERT INTO photos (original_filename, stored_filename, file_path, file_size, content_type, extension, user_id)
-- VALUES ('test.jpg', 'abc123.jpg', '/uploads/abc123.jpg', 1024000, 'image/jpeg', 'jpg', 'admin');

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(BCrypt加密)',
    enabled BOOLEAN DEFAULT TRUE NOT NULL COMMENT '账户是否启用',
    locked BOOLEAN DEFAULT FALSE NOT NULL COMMENT '账户是否锁定',
    failed_attempts INT DEFAULT 0 NOT NULL COMMENT '连续失败登录次数',
    locked_time TIMESTAMP NULL COMMENT '账户锁定时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 记住我Token表
CREATE TABLE IF NOT EXISTS remember_me_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    token VARCHAR(100) NOT NULL UNIQUE COMMENT 'Token值',
    expires_at TIMESTAMP NOT NULL COMMENT 'Token过期时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Token创建时间',
    
    INDEX idx_token (token),
    INDEX idx_username (username),
    INDEX idx_expires_at (expires_at),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记住我Token表';

-- 插入默认管理员账户
-- 用户名: admin
-- 密码: admin123 (BCrypt加密后的值)
INSERT INTO users (username, password, enabled, locked, failed_attempts)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', TRUE, FALSE, 0)
ON DUPLICATE KEY UPDATE username = username;
