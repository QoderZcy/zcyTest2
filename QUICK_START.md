# 登录功能快速启动指南

## 🚀 快速开始（5分钟）

### 1. 启动应用

选择以下任一方式启动：

**方式A：使用Maven**
```bash
cd /data/workspace/zcyTest2
mvn spring-boot:run
```

**方式B：使用IDE**
1. 在IDE中打开项目
2. 找到 `PhotoUploadApplication.java`
3. 右键运行

**方式C：使用jar包**
```bash
mvn clean package
java -jar target/*.jar
```

### 2. 访问登录页面

在浏览器中打开：
```
http://localhost:8080/login
```

### 3. 登录系统

使用默认管理员账户：
- **用户名**：`admin`
- **密码**：`admin123`

点击"登录"按钮，成功后会跳转到首页。

## ✅ 功能验证清单

- [ ] 能访问登录页面
- [ ] 使用admin/admin123可以登录
- [ ] 错误密码会提示"用户名或密码错误"
- [ ] 勾选"记住我"后，关闭浏览器重开仍保持登录
- [ ] 点击登出后返回登录页面

## 📋 测试账户

| 用户名 | 密码 | 说明 |
|--------|------|------|
| admin | admin123 | 默认管理员账户 |

## 🔧 常见问题

### Q1: 启动失败，端口被占用
**解决**：修改 `application.yml` 中的端口
```yaml
server:
  port: 8081  # 改为其他端口
```

### Q2: 访问登录页返回404
**解决**：确认 `application.yml` 中没有设置 `context-path`

### Q3: 登录后仍然停留在登录页
**解决**：检查浏览器控制台错误，查看应用日志

### Q4: 记住我功能不工作
**解决**：检查浏览器是否允许Cookie

## 📚 相关文档

- **详细部署指南**：`LOGIN_DEPLOYMENT_GUIDE.md`
- **实施总结**：`LOGIN_IMPLEMENTATION_SUMMARY.md`
- **设计文档**：`.qoder/quests/simple-login-page.md`

## 🛠️ 数据库访问（开发环境）

访问H2控制台查看数据库：
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/photodb
Username: sa
Password: (留空)
```

查看用户表：
```sql
SELECT * FROM users;
```

查看Token表：
```sql
SELECT * FROM remember_me_tokens;
```

## 📊 应用日志

日志文件位置：
```
./logs/photo-upload-system.log
```

实时查看日志：
```bash
tail -f logs/photo-upload-system.log
```

## ⚠️ 安全提醒

1. **立即修改默认密码**：生产环境部署前务必修改admin的密码
2. **启用HTTPS**：生产环境必须使用HTTPS
3. **定期备份**：定期备份用户数据和Token数据

## 🎯 下一步

登录功能验证成功后，可以：
1. 修改默认管理员密码
2. 创建新用户
3. 测试记住我功能
4. 配置生产环境数据库
5. 添加更多用户管理功能

---

**需要帮助？** 查看详细文档或检查应用日志
