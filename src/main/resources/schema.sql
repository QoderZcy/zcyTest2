-- 便签管理系统数据库初始化脚本
-- 创建便签表

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

-- 创建用户名索引
CREATE INDEX IF NOT EXISTS idx_username ON users(username);

-- 插入默认测试用户（密码：123456）
INSERT INTO users (username, password, email, enabled) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'admin@example.com', TRUE);

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

-- 博客管理系统数据库初始化脚本
-- 创建博客表

CREATE TABLE IF NOT EXISTS blogs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    author VARCHAR(100) NOT NULL,
    content TEXT,
    category VARCHAR(50),
    tags VARCHAR(200),
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL
);

-- 创建索引以优化查询性能
CREATE INDEX IF NOT EXISTS idx_blog_created_time ON blogs(created_time);
CREATE INDEX IF NOT EXISTS idx_blog_author ON blogs(author);

-- 插入博客示例数据
INSERT INTO blogs (title, author, content, category, tags, created_time, updated_time) VALUES 
(
    '欢迎来到博客系统', 
    '系统管理员',
    '# 欢迎来到博客系统 ✨

## 关于本系统

这是一个现代化的博客管理系统，采用赛博朋克设计风格，为您提供优雅的写作体验。

## 主要特性

### 强大的编辑功能
- ✅ **Markdown支持**：完整的Markdown语法支持
- ✅ **实时预览**：编辑时即时查看渲染效果
- ✅ **分类管理**：为博客添加分类标签
- ✅ **标签系统**：使用标签组织内容

### 赛博朋克设计
- 🎨 霓虹色调与发光效果
- 🎨 流畅的动画交互
- 🎨 响应式布局设计

## Markdown语法示例

### 文本格式
这是普通文本，**这是粗体**，*这是斜体*

### 代码示例
```java
public class Blog {
    private String title;
    private String author;
    private String content;
}
```

### 引用
> 写作是思考的延伸，博客是记录思想的最好方式。

### 列表
1. 创建博客
2. 编辑内容
3. 发布分享

开始您的写作之旅吧！',
    '技术',
    'Markdown,博客,教程',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
