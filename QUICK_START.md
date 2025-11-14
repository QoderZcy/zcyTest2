# 快速启动指南

## 启动应用

1. **确保环境准备**
   - JDK 17+
   - Maven 3.6+

2. **启动应用**
   ```bash
   cd /data/workspace/zcyTest2
   mvn spring-boot:run
   ```

3. **访问应用**
   - 登录页面: http://localhost:8080/api/login.html
   - 注册页面: http://localhost:8080/api/register.html
   - API文档: http://localhost:8080/api/swagger-ui.html

## 快速测试流程

### 1. 注册新用户
1. 访问 http://localhost:8080/api/register.html
2. 填写信息:
   - 用户名: demo
   - 密码: 123456
   - 确认密码: 123456
   - 邮箱: demo@example.com (可选)
3. 点击"注册"
4. 等待3秒自动跳转到登录页面

### 2. 用户登录
1. 在登录页面输入:
   - 用户名: demo
   - 密码: 123456
2. 可选择勾选"记住我"（30天）
3. 点击"登录"
4. 自动跳转到照片上传系统主页

### 3. 使用照片上传系统
1. 登录成功后，看到欢迎信息和退出按钮
2. 选择图片文件进行上传
3. 查看已上传的照片
4. 点击"退出登录"返回登录页面

## 验证要点

- ✅ 未登录时访问主页会自动跳转到登录页面
- ✅ 登录成功后显示用户名
- ✅ 上传照片时自动关联当前用户
- ✅ 只能查看和管理自己的照片
- ✅ Token过期后自动跳转到登录页面
- ✅ 退出登录后清除认证信息

## 数据库查看（H2控制台）

1. 访问: http://localhost:8080/api/h2-console
2. JDBC URL: jdbc:h2:mem:testdb
3. 用户名: sa
4. 密码: (留空)
5. 查看users表和photos表的数据

## API测试示例

### 注册用户
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"api_user","password":"123456","email":"api@example.com"}'
```

### 登录获取Token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"api_user","password":"123456"}'
```

### 上传照片（需要Token）
```bash
TOKEN="<上一步返回的token>"
curl -X POST http://localhost:8080/api/photos/upload \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@test.jpg"
```

### 获取当前用户照片列表
```bash
curl -X GET "http://localhost:8080/api/photos/user?page=0&size=20" \
  -H "Authorization: Bearer $TOKEN"
```

## 故障排查

### 问题1: 无法启动应用
- 检查端口8080是否被占用
- 检查JDK版本是否为17+
- 查看启动日志中的错误信息

### 问题2: 登录失败
- 确认用户名和密码正确
- 检查数据库中是否存在该用户
- 查看后端日志中的错误信息

### 问题3: Token验证失败
- 检查Token格式是否正确（Bearer + 空格 + token）
- 确认Token未过期
- 查看浏览器控制台的网络请求

### 问题4: 跨域问题
- 确认SecurityConfig中CORS配置正确
- 检查application.yml中的CORS设置

## 默认配置

### JWT配置 (可在application.yml中覆盖)
```yaml
jwt:
  secret: MySecretKeyForJWTTokenGenerationAndValidation2024PhotoUploadSystem
  expiration: 604800000      # 7天 (毫秒)
  rememberMeExpiration: 2592000000  # 30天 (毫秒)
  tokenPrefix: "Bearer "
  headerName: "Authorization"
```

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: update
```

## 生产环境部署建议

1. **修改JWT密钥**
   - 使用环境变量配置
   - 使用更复杂的密钥

2. **使用MySQL数据库**
   - 修改数据源配置
   - 执行schema.sql创建表

3. **启用HTTPS**
   - 配置SSL证书
   - 强制HTTPS访问

4. **配置日志**
   - 设置合适的日志级别
   - 配置日志文件轮转

5. **性能优化**
   - 配置连接池
   - 启用缓存
   - 设置合理的超时时间
