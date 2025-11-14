# 登录功能部署和测试指南

## 一、部署前检查清单

### 1.1 确认文件已创建
以下文件应该已经成功创建：

**实体类**
- ✅ `src/main/java/com/photo/entity/User.java`
- ✅ `src/main/java/com/photo/entity/RememberMeToken.java`

**仓储接口**
- ✅ `src/main/java/com/photo/repository/UserRepository.java`
- ✅ `src/main/java/com/photo/repository/RememberMeTokenRepository.java`

**服务类**
- ✅ `src/main/java/com/photo/service/UserDetailsServiceImpl.java`
- ✅ `src/main/java/com/photo/service/CustomRememberMeService.java`

**配置类**
- ✅ `src/main/java/com/photo/config/SecurityConfig.java` (已修改)

**前端页面**
- ✅ `src/main/resources/static/login.html`
- ✅ `src/main/resources/static/css/login.css`
- ✅ `src/main/resources/static/js/login.js`

**数据库脚本**
- ✅ `src/main/resources/schema.sql` (已添加用户表和Token表)

**配置文件**
- ✅ `src/main/resources/application.yml` (已更新)

### 1.2 确认依赖已配置
项目使用的是Spring Boot 3.2.0，需要确认pom.xml中包含以下依赖：
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- H2 Database (开发环境)
- Lombok

## 二、启动应用

### 2.1 使用Maven启动
```bash
cd /data/workspace/zcyTest2
mvn clean install
mvn spring-boot:run
```

### 2.2 使用IDE启动
1. 在IDE中打开项目
2. 找到主类 `PhotoUploadApplication.java`
3. 右键选择 "Run" 或 "Debug"

### 2.3 使用jar包启动
```bash
cd /data/workspace/zcyTest2
mvn clean package
java -jar target/photo-upload-system-*.jar
```

## 三、数据库初始化

### 3.1 H2数据库（开发环境）
应用启动时会自动执行以下操作：
1. 创建 `users` 表
2. 创建 `remember_me_tokens` 表
3. 插入默认管理员账户
   - 用户名: `admin`
   - 密码: `admin123`

### 3.2 验证数据库
访问H2控制台验证表是否创建成功：
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/photodb`
- Username: `sa`
- Password: (留空)

执行SQL查询验证：
```sql
-- 查看用户表
SELECT * FROM users;

-- 查看Token表结构
SELECT * FROM remember_me_tokens;
```

## 四、功能测试

### 4.1 访问登录页面
1. 启动应用后，在浏览器中访问：
   - http://localhost:8080/login

2. 应该看到登录页面，包含：
   - 系统标题 "照片上传系统"
   - 用户名输入框
   - 密码输入框
   - 记住我复选框
   - 登录按钮

### 4.2 测试场景

#### 场景一：正常登录
1. 输入用户名: `admin`
2. 输入密码: `admin123`
3. 点击"登录"按钮
4. **预期结果**: 跳转到首页 `/index.html`

#### 场景二：错误密码
1. 输入用户名: `admin`
2. 输入密码: `wrongpassword`
3. 点击"登录"按钮
4. **预期结果**: 
   - 跳转回登录页面
   - 显示错误提示 "用户名或密码错误"
   - 用户的失败尝试次数加1

#### 场景三：错误用户名
1. 输入用户名: `nonexistent`
2. 输入密码: `admin123`
3. 点击"登录"按钮
4. **预期结果**: 显示 "用户名或密码错误"

#### 场景四：表单验证
1. 用户名留空，直接点击登录
2. **预期结果**: 显示 "请输入用户名"

3. 输入用户名 `ab` (少于3个字符)
4. **预期结果**: 显示 "用户名长度应为3-20个字符"

5. 输入密码 `12345` (少于6个字符)
6. **预期结果**: 显示 "密码长度应为6-20个字符"

#### 场景五：记住我功能
1. 输入正确的用户名和密码
2. **勾选** "记住我7天"
3. 点击登录
4. 登录成功后，关闭浏览器
5. 重新打开浏览器，访问 http://localhost:8080/index.html
6. **预期结果**: 自动登录，无需输入凭证

#### 场景六：登出功能
1. 登录后，访问: http://localhost:8080/logout (使用POST方法)
2. **预期结果**: 
   - 跳转到登录页面
   - 显示 "您已成功登出"
   - 会话被清除
   - 记住我Token被删除

#### 场景七：账户锁定
1. 连续5次输入错误密码
2. **预期结果**: 
   - 账户被锁定
   - 显示 "账户已被锁定，请稍后再试"
   - 30分钟后自动解锁

#### 场景八：访问受保护资源
1. 未登录状态下，直接访问 http://localhost:8080/index.html
2. **预期结果**: 自动重定向到登录页面

### 4.3 CSRF保护验证
登录页面会自动包含CSRF Token，可以通过浏览器开发者工具查看：
1. 打开浏览器开发者工具 (F12)
2. 切换到 "Network" 标签
3. 提交登录表单
4. 查看请求参数，应该包含 `_csrf` 字段

## 五、日志监控

### 5.1 查看应用日志
应用日志会输出到：
- 控制台
- 文件: `./logs/photo-upload-system.log`

### 5.2 关键日志信息
登录过程中会输出以下日志：

**成功登录**
```
INFO  c.p.s.UserDetailsServiceImpl - 用户 admin 登录成功
INFO  c.p.s.CustomRememberMeService - 为用户 admin 创建记住我Token
```

**登录失败**
```
WARN  c.p.s.UserDetailsServiceImpl - 用户 admin 登录失败
DEBUG c.p.s.UserDetailsServiceImpl - 用户 admin 失败尝试次数: 1
```

**账户锁定**
```
WARN  c.p.s.UserDetailsServiceImpl - 用户 admin 连续失败5次，账户已被锁定
```

**自动登录**
```
INFO  c.p.s.CustomRememberMeService - 用户 admin 通过记住我Token自动登录
```

## 六、常见问题排查

### 6.1 无法访问登录页面
**问题**: 访问 http://localhost:8080/login 返回404

**解决方案**:
1. 确认 `login.html` 文件在 `src/main/resources/static/` 目录下
2. 确认 `application.yml` 中没有设置 `context-path`
3. 检查 `SecurityConfig` 中 `/login` 路径是否在 `permitAll()` 列表中

### 6.2 登录后仍然跳转到登录页
**问题**: 输入正确凭证后，仍然停留在登录页面

**解决方案**:
1. 检查 `SecurityConfig` 中的认证配置
2. 确认 `UserDetailsServiceImpl` 正确加载用户
3. 检查密码是否使用BCrypt加密
4. 查看应用日志中的错误信息

### 6.3 CSRF Token错误
**问题**: 提交登录表单时返回403 Forbidden

**解决方案**:
1. 确认登录页面包含隐藏的 `_csrf` 字段
2. 检查 `login.js` 中是否正确获取CSRF Token
3. 确认 `SecurityConfig` 中启用了CSRF保护

### 6.4 记住我功能不工作
**问题**: 勾选记住我后，重启浏览器仍需重新登录

**解决方案**:
1. 检查 `remember_me_tokens` 表是否存在
2. 确认 `CustomRememberMeService` 正确注入
3. 检查浏览器是否允许Cookie
4. 查看数据库中是否成功保存Token

### 6.5 数据库初始化失败
**问题**: 启动时报错，用户表创建失败

**解决方案**:
1. 检查 `schema.sql` 语法是否正确
2. 确认JPA配置中 `ddl-auto` 设置为 `update`
3. 删除 `./data/photodb.mv.db` 文件，重新初始化
4. 查看启动日志中的数据库错误信息

## 七、性能优化建议

### 7.1 生产环境配置
部署到生产环境时，建议进行以下调整：

1. **切换到MySQL数据库**
   - 修改 `application.yml` 中的数据源配置
   - 使用MySQL替代H2数据库

2. **启用HTTPS**
   - 在 `CustomRememberMeService` 中取消注释 `cookie.setSecure(true)`
   - 配置SSL证书

3. **调整会话和Token有效期**
   - 根据实际需求调整 `token-validity-seconds`
   - 设置合理的会话超时时间

4. **启用日志脱敏**
   - 避免在日志中输出敏感信息（密码、Token等）

5. **添加验证码**
   - 防止暴力破解攻击
   - 可使用Google reCAPTCHA或图形验证码

### 7.2 定时任务配置
建议添加定时任务清理过期Token：

```java
@Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行
public void cleanupExpiredTokens() {
    customRememberMeService.cleanupExpiredTokens();
}
```

## 八、安全加固建议

1. **修改默认密码**
   - 首次登录后立即修改admin的默认密码

2. **配置密码策略**
   - 强制密码复杂度（大小写、数字、特殊字符）
   - 定期强制修改密码

3. **IP限流**
   - 添加IP级别的登录限流
   - 防止分布式暴力破解

4. **登录审计**
   - 记录所有登录尝试（成功和失败）
   - 便于安全分析和追溯

5. **多因素认证**
   - 考虑添加短信或邮箱验证
   - 提高账户安全性

## 九、测试完成确认

完成以下测试后，可以认为登录功能部署成功：

- [ ] 能够访问登录页面
- [ ] 使用正确凭证可以成功登录
- [ ] 错误凭证显示正确的错误提示
- [ ] 前端表单验证正常工作
- [ ] 记住我功能正常工作
- [ ] 登出功能正常工作
- [ ] 连续失败5次后账户被锁定
- [ ] 未登录访问受保护资源会重定向到登录页
- [ ] CSRF保护正常工作
- [ ] 数据库中正确记录用户和Token信息

## 十、下一步扩展

基本登录功能完成后，可以考虑以下扩展：

1. **用户注册功能**
   - 允许新用户自助注册
   - 邮箱验证

2. **找回密码功能**
   - 通过邮箱重置密码
   - 密码重置Token

3. **用户管理界面**
   - 管理员可以管理用户账户
   - 查看登录日志

4. **角色和权限**
   - 实现基于角色的访问控制（RBAC）
   - 不同角色有不同的权限

5. **OAuth2集成**
   - 支持第三方登录（GitHub、Google等）
   - 提升用户体验
