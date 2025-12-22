-- 便签管理系统数据库初始化脚本
-- 创建便签表和用户认证表

CREATE TABLE IF NOT EXISTS notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL
);

-- 创建索引以优化查询性能
CREATE INDEX IF NOT EXISTS idx_created_time ON notes(created_time);

-- 插入示例数据（可选）
INSERT INTO notes (title, content, created_time, updated_time) VALUES 
(
    '欢迎使用便签管理系统', 
    '# 欢迎使用便签管理系统 🎉

## 功能特性

这是一个赛博朋克风格的便签管理系统，支持以下功能：

- ✅ **Markdown格式**：支持完整的Markdown语法
- ✅ **实时预览**：编辑时实时查看渲染效果
- ✅ **快捷键支持**：Ctrl+S保存，Ctrl+B加粗等
- ✅ **赛博朋克风格**：霓虹色调、发光效果

## Markdown示例

### 文本样式
**粗体文本** 和 *斜体文本*

### 列表
- 便签列表
- 便签详情
- 便签编辑

### 代码块
```java
public class Note {
    private String title;
    private String content;
}
```

### 引用
> 记录你的想法，组织你的生活

开始创建你的第一个便签吧！',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ========================================
-- 用户认证系统表
-- ========================================

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    failed_login_attempts INT NOT NULL DEFAULT 0,
    lock_time TIMESTAMP NULL,
    last_login_time TIMESTAMP NULL,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建用户名索引
CREATE UNIQUE INDEX IF NOT EXISTS idx_username ON users(username);

-- 创建RememberMe持久化Token表
CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);

-- 插入默认测试用户
-- admin用户密码: admin123 (BCrypt加密后)
-- guest用户密码: guest123 (BCrypt加密后)
INSERT INTO users (username, password, enabled, account_non_locked) VALUES 
('admin', '$2a$10$X5wFuJKKXXEeFV4fLKSKPe5HlJQxj6QFXhZd8LJQvTJvOFJh8Lw9O', TRUE, TRUE),
('guest', '$2a$10$qB0KH1U3l.zXKZLXVQNJTeCk8h6fG3Z7jQz8ZwLxJ5K8DYhKJmE1G', TRUE, TRUE);
