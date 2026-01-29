# 登录功能说明

## 功能概述

已成功实现完整的用户登录系统，包括：
- ✅ 用户登录
- ✅ 用户注册
- ✅ 会话管理
- ✅ 访问控制

## 访问地址

登录页面: `http://localhost:8080/auth/login`

## 测试账户

系统已自动创建以下测试账户：

| 用户名 | 密码 | 邮箱 |
|--------|------|------|
| admin | 123456 | admin@example.com |
| test | 123456 | test@example.com |

## 功能说明

### 1. 登录页面

- **登录表单**：用户名/密码输入，带基础校验
- **注册表单**：支持新用户注册（用户名、邮箱、密码）
- **实时反馈**：登录/注册成功或失败的即时提示
- **页面切换**：登录和注册标签页切换

### 2. 安全配置

- **密码加密**：使用BCrypt加密存储
- **会话管理**：基于Session的认证
- **访问控制**：
  - 公开路径：`/auth/**`, `/css/**`, `/js/**`, `/images/**`
  - 需认证：其他所有路径
- **表单登录**：支持标准表单登录
- **登出功能**：支持用户登出

### 3. 数据库表结构

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);
```

## 使用流程

### 启动应用

```bash
# 确保数据库配置正确
# 启动Spring Boot应用
./start.sh
# 或
mvn spring-boot:run
```

### 访问系统

1. 打开浏览器访问: `http://localhost:8080`
2. 系统会自动跳转到登录页面: `http://localhost:8080/auth/login`
3. 使用测试账户登录（admin/123456 或 test/123456）
4. 登录成功后跳转到首页

### 注册新用户

1. 在登录页面点击"注册"标签
2. 填写用户名（至少3个字符）
3. 填写邮箱（可选）
4. 设置密码（至少6个字符）
5. 确认密码
6. 点击"注册"按钮
7. 注册成功后自动切换到登录页面

## API接口

### 登录接口

```
POST /auth/login
Content-Type: application/x-www-form-urlencoded

username=admin&password=123456
```

响应：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "message": "登录成功",
    "username": "admin",
    "redirectUrl": "/"
  }
}
```

### 注册接口

```
POST /auth/register
Content-Type: application/x-www-form-urlencoded

username=newuser&password=123456&email=user@example.com
```

响应：
```json
{
  "code": 200,
  "message": "success",
  "data": "注册成功"
}
```

### 登出接口

```
POST /auth/logout
```

响应：
```json
{
  "code": 200,
  "message": "success",
  "data": "注销成功"
}
```

## 代码结构

```
src/main/java/com/photo/
├── entity/
│   └── User.java                    # 用户实体
├── repository/
│   └── UserRepository.java          # 用户数据访问
├── service/
│   └── CustomUserDetailsService.java # 用户认证服务
├── controller/
│   └── AuthController.java          # 认证控制器
├── config/
│   ├── SecurityConfig.java          # Security配置
│   └── DataInitializer.java         # 数据初始化

src/main/resources/
├── templates/
│   └── login.html                   # 登录页面
└── schema.sql                       # 数据库脚本
```

## 特性说明

### 前端特性
- 响应式设计，支持移动端
- 渐变背景和现代化UI
- 表单验证和错误提示
- 加载动画效果
- 登录/注册标签页切换

### 后端特性
- Spring Security集成
- BCrypt密码加密
- Session会话管理
- 统一异常处理
- 最后登录时间记录

## 注意事项

1. **密码安全**：默认测试密码为123456，生产环境请修改
2. **Session配置**：当前使用Session认证，可根据需要改为JWT
3. **CORS配置**：已禁用CSRF，生产环境请根据需要启用
4. **数据库**：使用H2内存数据库，重启后数据会重置

## 后续优化建议

1. 添加验证码功能
2. 实现记住我功能
3. 添加角色权限管理
4. 邮箱验证和找回密码
5. 登录日志记录
6. 防暴力破解限制
