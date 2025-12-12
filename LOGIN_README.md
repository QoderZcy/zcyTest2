# 登录功能使用说明

## 功能概述

本系统已实现基于Session的登录认证功能,用户需要登录后才能访问照片上传和管理功能。

## 快速开始

### 1. 启动应用

```bash
# 使用Maven启动
mvn spring-boot:run

# 或使用Java命令
java -jar target/photo-upload-system-1.0.0.jar
```

### 2. 访问登录页面

打开浏览器访问: http://localhost:8080/login.html

### 3. 使用测试账户登录

系统预置了两个测试账户:

**管理员账户:**
- 用户名: `admin`
- 密码: `admin123`

**普通用户账户:**
- 用户名: `user`
- 密码: `user123`

### 4. 登录成功后

登录成功后会自动跳转到照片上传系统主页 (http://localhost:8080/index.html)

## 功能特性

### 1. 登录验证

- **前端验证**: 
  - 用户名长度: 3-20个字符
  - 密码长度: 6-20个字符
  - 必填项检查

- **后端验证**:
  - 用户名密码匹配
  - 账户状态检查(是否启用/锁定)
  - 密码使用BCrypt加密存储

### 2. 会话管理

- **默认会话**: 30分钟无操作自动过期
- **记住我功能**: 勾选"记住我"后,会话有效期延长至7天
- **并发控制**: 同一用户最多1个活动会话

### 3. 安全防护

#### 暴力破解防护
- 登录失败5次后,账户自动锁定10分钟
- 锁定期间无法登录
- 锁定时间过后自动解锁

#### 密码安全
- 密码使用BCrypt算法加密存储
- 不保存明文密码
- 登录失败不提示具体原因(用户不存在/密码错误)

#### CSRF防护
- 已集成Spring Security的CSRF保护
- 推荐生产环境启用

### 4. 用户界面

#### 登录页面特性
- 响应式设计,支持移动端和桌面端
- 密码显示/隐藏切换功能
- 实时错误提示
- 加载状态显示
- 与主系统统一的视觉风格

#### 主页面增强
- 显示当前登录用户名
- 提供登出按钮
- 未登录自动跳转到登录页

## API接口

### 1. 登录接口

**请求:**
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123",
  "rememberMe": false
}
```

**成功响应:**
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "userId": 1,
    "username": "admin",
    "token": "SESSION",
    "message": "登录成功"
  }
}
```

**失败响应:**
```json
{
  "code": 401,
  "message": "用户名或密码错误"
}
```

### 2. 登出接口

**请求:**
```
POST /api/auth/logout
```

**响应:**
```json
{
  "code": 200,
  "message": "登出成功",
  "data": "登出成功"
}
```

### 3. 检查登录状态接口

**请求:**
```
GET /api/auth/status
```

**已登录响应:**
```json
{
  "code": 200,
  "message": "已登录",
  "data": {
    "userId": 1,
    "username": "admin",
    "message": "已登录"
  }
}
```

**未登录响应:**
```json
{
  "code": 401,
  "message": "未登录"
}
```

## 访问控制规则

| 路径 | 访问控制 | 说明 |
|------|----------|------|
| `/login.html` | 匿名可访问 | 登录页面 |
| `/api/auth/login` | 匿名可访问 | 登录接口 |
| `/api/auth/logout` | 需要认证 | 登出接口 |
| `/api/auth/status` | 匿名可访问 | 状态检查接口 |
| `/index.html` | 需要认证 | 主页面 |
| `/api/photos/**` | 需要认证 | 照片管理接口 |
| `/css/**`, `/js/**` | 匿名可访问 | 静态资源 |
| `/h2-console/**` | 匿名可访问 | H2数据库控制台(开发环境) |

## 数据库结构

### 用户表 (users)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键,自增 |
| username | VARCHAR(50) | 用户名,唯一 |
| password | VARCHAR(100) | 密码哈希值 |
| enabled | BOOLEAN | 是否启用 |
| account_non_expired | BOOLEAN | 账户是否未过期 |
| account_non_locked | BOOLEAN | 账户是否未锁定 |
| credentials_non_expired | BOOLEAN | 凭证是否未过期 |
| created_at | TIMESTAMP | 创建时间 |
| last_login_at | TIMESTAMP | 最后登录时间 |
| failed_login_attempts | INT | 登录失败次数 |
| locked_until | TIMESTAMP | 锁定截止时间 |

## 测试

### 单元测试

运行认证服务测试:
```bash
mvn test -Dtest=AuthServiceTest
```

测试覆盖场景:
- ✅ 登录成功
- ✅ 密码错误
- ✅ 用户不存在
- ✅ 账户被禁用
- ✅ 账户被锁定
- ✅ 达到最大失败次数后锁定
- ✅ 初始化默认用户

### 手动测试

1. **正常登录流程**:
   - 访问登录页面
   - 输入正确的用户名密码
   - 点击登录
   - 验证跳转到主页面

2. **登录失败测试**:
   - 输入错误的密码
   - 验证显示错误提示
   - 验证失败次数增加

3. **账户锁定测试**:
   - 连续5次输入错误密码
   - 验证账户被锁定
   - 等待10分钟后重试

4. **记住我功能测试**:
   - 勾选"记住我"
   - 登录成功
   - 关闭浏览器重新打开
   - 验证仍然保持登录状态

5. **登出测试**:
   - 登录后点击登出按钮
   - 验证跳转到登录页面
   - 验证无法直接访问主页面

## 故障排查

### 1. 无法访问登录页面

**问题**: 访问 /login.html 返回404

**解决**:
- 检查应用是否正常启动
- 确认静态资源路径配置正确
- 查看日志是否有错误信息

### 2. 登录后立即跳回登录页

**问题**: 登录成功但马上又跳转回登录页面

**解决**:
- 检查浏览器是否禁用Cookie
- 查看浏览器控制台是否有JavaScript错误
- 确认Session配置正确

### 3. 所有账户都无法登录

**问题**: 提示"用户名或密码错误"

**解决**:
- 检查数据库是否正常初始化
- 查看日志确认默认用户是否创建成功
- 使用H2控制台查询users表数据

### 4. 账户被意外锁定

**问题**: 正常账户显示被锁定

**解决**:
- 等待10分钟自动解锁
- 或手动更新数据库:
  ```sql
  UPDATE users 
  SET failed_login_attempts = 0, locked_until = NULL 
  WHERE username = 'admin';
  ```

## 安全建议

### 生产环境配置

1. **启用HTTPS**: 确保密码传输加密
2. **修改默认密码**: 删除或修改测试账户密码
3. **配置强密码策略**: 要求更复杂的密码
4. **启用CSRF保护**: 防止跨站请求伪造攻击
5. **配置日志监控**: 监控异常登录行为
6. **定期密码更新**: 强制用户定期更换密码

### 扩展功能建议

1. 用户注册功能
2. 密码找回功能
3. 双因素认证(2FA)
4. 验证码防护
5. IP白名单限制
6. 登录历史记录
7. 用户权限管理

## 相关文件

### 后端代码
- `User.java` - 用户实体类
- `UserRepository.java` - 用户仓库接口
- `LoginRequest.java` - 登录请求DTO
- `LoginResponse.java` - 登录响应DTO
- `AuthService.java` - 认证服务
- `AuthController.java` - 认证控制器
- `SecurityConfig.java` - Spring Security配置
- `DataInitializer.java` - 数据初始化器

### 前端代码
- `login.html` - 登录页面
- `index.html` - 主页面(已更新)

### 数据库脚本
- `schema.sql` - 数据库表结构

### 测试代码
- `AuthServiceTest.java` - 认证服务测试

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **安全框架**: Spring Security
- **持久层**: Spring Data JPA
- **数据库**: H2 (可切换为MySQL)
- **密码加密**: BCrypt
- **前端**: HTML5 + CSS3 + JavaScript

## 联系支持

如有问题或建议,请联系开发团队。
