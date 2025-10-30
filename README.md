# Photo Upload System - 照片上传下载系统

## 项目简介

基于Spring Boot 3.2.0开发的企业级照片上传下载系统，提供完整的文件管理功能，包括上传、下载、在线预览、断点续传等。

## 核心功能

### 1. 文件上传
- ✅ 单文件上传
- ✅ 多文件批量上传
- ✅ 文件类型验证（仅支持图片格式）
- ✅ 文件大小限制（最大10MB）
- ✅ 自动生成唯一文件名（UUID）
- ✅ MD5去重检测
- ✅ 上传进度显示支持

### 2. 文件下载
- ✅ 文件下载接口
- ✅ 断点续传支持（Range请求）
- ✅ 在线预览功能
- ✅ 访问权限控制
- ✅ 下载次数统计

### 3. 安全特性
- ✅ 文件类型安全检查（Apache Tika）
- ✅ 文件名安全处理（防路径遍历）
- ✅ 访问权限控制
- ✅ 防盗链措施（Referer验证）
- ✅ XSS防护
- ✅ CORS配置
- ✅ Spring Security集成

### 4. 性能优化
- ✅ 文件上传进度监控
- ✅ 大文件处理优化
- ✅ 图片压缩功能（Thumbnailator）
- ✅ 缩略图自动生成
- ✅ Caffeine缓存机制
- ✅ 数据库索引优化

### 5. 存储管理
- ✅ 文件存储路径配置
- ✅ 定期清理机制（定时任务）
- ✅ 存储容量监控
- ✅ 软删除支持
- ✅ 存储空间统计

### 6. API接口
- ✅ RESTful API设计
- ✅ Swagger/OpenAPI文档
- ✅ 标准的响应格式
- ✅ 全局异常处理

## 技术栈

- **框架**: Spring Boot 3.2.0
- **数据库**: H2 (开发) / MySQL (生产)
- **ORM**: Spring Data JPA
- **安全**: Spring Security
- **缓存**: Caffeine
- **文件处理**: Apache Commons IO, Apache Tika
- **图片处理**: Thumbnailator
- **API文档**: SpringDoc OpenAPI 3
- **测试**: JUnit 5, Mockito

## 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 5.7+ (生产环境)

### 2. 配置数据库
编辑 `src/main/resources/application.yml`，配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/photo_db
    username: root
    password: your_password
```

### 3. 编译项目
```bash
mvn clean package
```

### 4. 运行应用
```bash
mvn spring-boot:run
```

或运行编译后的jar包：
```bash
java -jar target/photo-upload-system-1.0.0.jar
```

### 5. 访问应用
- 应用主页: http://localhost:8080/api
- API文档: http://localhost:8080/api/swagger-ui.html
- H2控制台: http://localhost:8080/api/h2-console

## API文档

### 上传照片
```bash
POST /api/photos/upload
Content-Type: multipart/form-data

参数:
- file: 图片文件
- userId: 用户ID
- description: 描述(可选)
```

### 批量上传
```bash
POST /api/photos/upload/batch
Content-Type: multipart/form-data

参数:
- files: 图片文件数组
- userId: 用户ID
- description: 描述(可选)
```

### 在线预览
```bash
GET /api/photos/view/{filename}
```

### 下载文件
```bash
GET /api/photos/download/{filename}
```

### 断点续传下载
```bash
GET /api/photos/download/range/{filename}
Headers: Range: bytes=0-1023
```

### 获取照片信息
```bash
GET /api/photos/{id}
```

### 查询公开照片
```bash
GET /api/photos/public?page=0&size=20
```

### 搜索照片
```bash
GET /api/photos/search?keyword=test&page=0&size=20
```

### 删除照片
```bash
DELETE /api/photos/{id}?userId=xxx
```

### 获取存储信息
```bash
GET /api/photos/storage/info
```

## 配置说明

### 文件存储配置
```yaml
file:
  storage:
    base-path: ./uploads          # 文件存储根目录
    max-file-size: 10485760       # 最大文件大小(10MB)
    max-storage-size: 10737418240 # 最大存储容量(10GB)
```

### 图片压缩配置
```yaml
file:
  storage:
    compression:
      enabled: true       # 是否启用压缩
      quality: 0.85       # 压缩质量
      max-width: 1920     # 最大宽度
      max-height: 1080    # 最大高度
```

### 定期清理配置
```yaml
file:
  storage:
    cleanup:
      enabled: true            # 是否启用定期清理
      days-to-keep: 30         # 保留天数
      cron: "0 0 2 * * ?"     # 执行时间(每天凌晨2点)
```

## 测试

运行单元测试：
```bash
mvn test
```

运行集成测试：
```bash
mvn verify
```

## 项目结构
```
src/main/java/com/photo/
├── common/              # 公共类
├── config/              # 配置类
│   ├── CacheConfig.java
│   ├── FileStorageProperties.java
│   ├── OpenApiConfig.java
│   ├── SecurityConfig.java
│   └── SecurityProperties.java
├── controller/          # 控制器
│   └── PhotoController.java
├── dto/                 # 数据传输对象
│   ├── ApiResponse.java
│   ├── PhotoDTO.java
│   ├── PhotoUploadResponse.java
│   ├── StorageInfo.java
│   └── UploadProgress.java
├── entity/              # 实体类
│   └── Photo.java
├── exception/           # 异常类
│   ├── AccessDeniedException.java
│   ├── FileException.java
│   ├── FileNotFoundException.java
│   ├── FileSizeException.java
│   ├── FileStorageException.java
│   ├── FileTypeException.java
│   ├── GlobalExceptionHandler.java
│   └── StorageFullException.java
├── repository/          # 数据访问层
│   └── PhotoRepository.java
├── service/             # 服务层
│   ├── FileStorageService.java
│   └── PhotoService.java
└── util/                # 工具类
    ├── FileUtils.java
    ├── ImageUtils.java
    └── SecurityUtils.java
```

## 注意事项

1. 生产环境请修改默认的安全配置（Token密钥等）
2. 建议使用专业的对象存储服务（如OSS、S3）替代本地存储
3. 定期备份数据库和上传文件
4. 监控存储空间使用情况
5. 根据实际需求调整文件大小和存储容量限制

## License

Apache License 2.0
