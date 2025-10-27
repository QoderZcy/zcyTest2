# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。## 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。 # 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。z# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。c# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。y# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。T# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。e# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。s# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。t# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。2# 登录页面项目

这是一个现代化的登录页面，使用HTML、CSS和JavaScript构建。

## 功能特性

- 🎨 现代化的UI设计，支持响应式布局
- 🔒 表单验证和用户身份验证
- 💾 "记住我"功能，使用localStorage存储
- 🎭 平滑的动画效果和交互反馈
- 📱 移动端适配
- ⚡ 快速加载和流畅的用户体验

## 项目结构

```
├── index.html      # 主HTML文件
├── styles.css      # CSS样式文件
├── script.js       # JavaScript功能文件
└── README.md       # 项目说明文档
```

## 如何使用

### 方法1：直接在浏览器中打开

直接双击 `index.html` 文件，或者右键选择"使用浏览器打开"。

### 方法2：使用HTTP服务器（推荐）

为了获得最佳体验，建议使用HTTP服务器：

**使用Python（如果已安装）：**
```bash
# Python 3
python3 -m http.server 8080

# Python 2
python -m SimpleHTTPServer 8080
```

**使用Node.js（如果已安装）：**
```bash
# 安装http-server
npm install -g http-server

# 启动服务器
http-server -p 8080
```

**使用PHP（如果已安装）：**
```bash
php -S localhost:8080
```

然后在浏览器中访问：`http://localhost:8080`

## 测试账户

为了测试登录功能，您可以使用以下测试账户：

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user | password | 普通用户 |
| test | test123 | 测试用户 |

## 功能说明

### 表单验证
- 用户名和密码不能为空
- 密码长度至少6位
- 实时的输入验证反馈

### 登录流程
1. 输入用户名和密码
2. 可选择"记住我"功能
3. 点击登录按钮
4. 系统验证用户信息
5. 登录成功后显示欢迎消息

### 其他功能
- 忘记密码链接（功能开发中）
- 社交登录按钮（Google、GitHub，功能开发中）
- 注册链接（功能开发中）

## 自定义和扩展

### 修改样式
编辑 `styles.css` 文件来自定义页面外观：
- 修改颜色主题
- 调整布局和间距
- 添加新的动画效果

### 添加功能
编辑 `script.js` 文件来扩展功能：
- 连接真实的后端API
- 添加更多验证规则
- 实现社交登录
- 添加注册和密码重置功能

### 连接后端
要连接真实的后端服务，您需要：
1. 修改 `handleLogin` 函数中的用户验证逻辑
2. 使用 `fetch` API 或其他HTTP客户端发送请求
3. 处理服务器响应和错误

示例代码：
```javascript
async function authenticateUser(username, password) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('登录失败');
        }
    } catch (error) {
        console.error('登录错误:', error);
        return null;
    }
}
```

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 现代样式，包括Flexbox、Grid、动画等
- **Vanilla JavaScript**: 原生JavaScript，无依赖
- **响应式设计**: 适配各种屏幕尺寸

## 浏览器支持

- Chrome 60+
- Firefox 60+
- Safari 12+
- Edge 79+

## 许可证

MIT License - 可自由使用和修改。

## 联系方式

如有问题或建议，请通过GitHub Issues联系。