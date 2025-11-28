# 便签管理系统 - 赛博朋克风格

一个简洁、现代的便签管理系统，采用赛博朋克视觉设计，支持Markdown格式和实时预览。

## 功能特性

### 核心功能
- ✅ **便签管理**：创建、编辑、删除、查看便签
- ✅ **Markdown支持**：完整的Markdown语法支持
- ✅ **实时预览**：编辑时实时查看渲染效果
- ✅ **快捷键支持**：提高操作效率
  - `Ctrl + S`: 保存便签
  - `Ctrl + B`: 加粗文本
  - `Ctrl + I`: 斜体文本
  - `Ctrl + K`: 插入链接
  - `Ctrl + Esc`: 取消编辑

### 赛博朋克风格设计
- 🌟 **霓虹色调**：蓝紫色系配色方案
- 💡 **发光效果**：按钮和边框的霓虹发光
- 🎨 **暗色背景**：深色主题保护视力
- ✨ **动画效果**：流畅的过渡和悬浮效果
- 📱 **响应式设计**：适配桌面和移动设备

## 技术栈

### 后端技术
- **Spring Boot 3.2**：核心框架
- **Spring Data JPA**：数据持久化
- **H2 Database**：嵌入式数据库
- **Thymeleaf**：服务端模板引擎
- **CommonMark**：Markdown解析器

### 前端技术
- **原生JavaScript**：页面交互
- **自定义CSS**：赛博朋克风格样式
- **响应式布局**：Grid和Flexbox

## 快速开始

### 环境要求
- JDK 17 或更高版本
- Maven 3.6+ （用于构建）

### 运行方式

#### 方式一：使用Maven运行（开发模式）
```bash
# 克隆或下载项目
cd zcyTest2

# 编译项目
mvn clean compile

# 运行应用
mvn spring-boot:run
```

#### 方式二：打包运行（生产模式）
```bash
# 构建可执行JAR
mvn clean package -DskipTests

# 运行JAR文件
java -jar target/photo-upload-system-1.0.0.jar
```

### 访问应用
启动成功后，在浏览器访问：
- 主页：http://localhost:8080/
- H2控制台：http://localhost:8080/h2-console

### H2数据库配置
- JDBC URL: `jdbc:h2:file:./data/notesdb`
- 用户名: `sa`
- 密码: (留空)

## 使用指南

### 创建便签
1. 点击右上角的"新建便签"按钮
2. 输入标题和内容（支持Markdown格式）
3. 右侧实时预览渲染效果
4. 点击"保存"按钮或按`Ctrl+S`保存

### 编辑便签
1. 在列表页面点击便签卡片进入详情
2. 点击"编辑"按钮
3. 修改内容后保存

### 删除便签
1. 在列表页面点击卡片右上角的删除按钮
2. 或在详情页面点击删除按钮
3. 确认后删除（不可恢复）

### Markdown语法示例

#### 标题
```markdown
# 一级标题
## 二级标题
### 三级标题
```

#### 文本样式
```markdown
**粗体文本**
*斜体文本*
~~删除线~~
```

#### 列表
```markdown
- 无序列表项1
- 无序列表项2

1. 有序列表项1
2. 有序列表项2
```

#### 代码
```markdown
行内代码：`code`

代码块：
\`\`\`java
public class Note {
    private String title;
}
\`\`\`
```

#### 引用
```markdown
> 这是一段引用文本
```

#### 链接
```markdown
[链接文本](http://example.com)
```

## 项目结构

```
zcyTest2/
├── src/
│   ├── main/
│   │   ├── java/com/photo/
│   │   │   ├── entity/          # 实体类
│   │   │   │   └── Note.java
│   │   │   ├── repository/      # 数据访问层
│   │   │   │   └── NoteRepository.java
│   │   │   ├── dto/             # 数据传输对象
│   │   │   │   └── NoteDTO.java
│   │   │   ├── service/         # 业务逻辑层
│   │   │   │   └── NoteService.java
│   │   │   ├── controller/      # 控制器层
│   │   │   │   └── NoteController.java
│   │   │   └── config/          # 配置类
│   │   └── resources/
│   │       ├── templates/notes/ # Thymeleaf模板
│   │       │   ├── list.html    # 列表页面
│   │       │   ├── detail.html  # 详情页面
│   │       │   └── edit.html    # 编辑页面
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── cyberpunk.css  # 赛博朋克样式
│   │       │   └── js/
│   │       │       ├── main.js        # 主脚本
│   │       │       └── editor.js      # 编辑器脚本
│   │       ├── application.yml  # 应用配置
│   │       └── schema.sql       # 数据库初始化
│   └── test/                    # 测试代码
├── pom.xml                      # Maven配置
└── README.md                    # 项目说明
```

## 配置说明

### 数据库配置
默认使用H2嵌入式数据库，数据存储在`./data/notesdb`目录。

如需切换到MySQL，修改`application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/notes_db
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: your_password
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

### 端口配置
默认端口为8080，可在`application.yml`中修改：
```yaml
server:
  port: 8080
```

### Thymeleaf配置
开发模式下禁用缓存以便实时查看修改：
```yaml
spring:
  thymeleaf:
    cache: false
```

## 开发说明

### 添加新功能
1. 实体层：在`entity`包中定义数据模型
2. 仓储层：在`repository`包中定义数据访问接口
3. 服务层：在`service`包中实现业务逻辑
4. 控制层：在`controller`包中定义路由和请求处理
5. 视图层：在`templates`中创建Thymeleaf模板

### 自定义样式
修改`static/css/cyberpunk.css`文件中的CSS变量：
```css
:root {
    --bg-primary: #0a0e27;
    --color-primary: #00f0ff;
    /* 更多变量... */
}
```

## 常见问题

### Q: 如何备份便签数据？
A: 复制`./data/notesdb.mv.db`文件即可备份所有数据。

### Q: 便签数据存储在哪里？
A: 使用H2数据库时，数据存储在项目根目录的`./data/notesdb.mv.db`文件中。

### Q: 如何清空所有便签？
A: 删除`./data/notesdb.mv.db`文件，重启应用即可。

### Q: 支持导入导出吗？
A: 当前版本暂不支持，可以通过H2控制台手动导出SQL。

### Q: 可以多人使用吗？
A: 当前版本为单用户设计，如需多用户支持，需要添加用户认证系统。

## 更新日志

### v1.0.0 (2025-11-28)
- ✨ 初始版本发布
- ✅ 基础便签管理功能
- ✅ Markdown支持和实时预览
- ✅ 赛博朋克视觉风格
- ✅ 快捷键支持
- ✅ 响应式设计

## 未来计划

- [ ] 便签分类和标签功能
- [ ] 全文搜索功能
- [ ] 便签置顶功能
- [ ] 便签归档功能
- [ ] 数据导入导出
- [ ] 多用户支持
- [ ] 云端同步
- [ ] 移动端App

## 许可证

MIT License

## 作者

韩寒

## 致谢

感谢以下开源项目：
- Spring Boot
- CommonMark
- Thymeleaf
- H2 Database
