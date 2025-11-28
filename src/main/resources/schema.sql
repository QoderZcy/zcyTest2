-- 便签管理系统数据库初始化脚本
-- 创建便签表

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
