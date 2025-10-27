// 登录页面JavaScript功能
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const rememberCheckbox = document.getElementById('remember');
    const loginButton = document.querySelector('.login-button');
    const messageDiv = document.getElementById('message');

    // 模拟用户数据（实际项目中应该从后端获取）
    const mockUsers = [
        { username: 'admin', password: '123456', name: '管理员' },
        { username: 'user', password: 'password', name: '普通用户' },
        { username: 'test', password: 'test123', name: '测试用户' }
    ];

    // 显示消息
    function showMessage(text, type = 'success') {
        messageDiv.textContent = text;
        messageDiv.className = `message ${type}`;
        messageDiv.classList.remove('hidden');
        
        setTimeout(() => {
            messageDiv.classList.add('hidden');
        }, 3000);
    }

    // 验证表单
    function validateForm() {
        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (!username) {
            showMessage('请输入用户名', 'error');
            usernameInput.focus();
            return false;
        }

        if (!password) {
            showMessage('请输入密码', 'error');
            passwordInput.focus();
            return false;
        }

        if (password.length < 6) {
            showMessage('密码长度至少为6位', 'error');
            passwordInput.focus();
            return false;
        }

        return true;
    }

    // 模拟登录验证
    function authenticateUser(username, password) {
        return mockUsers.find(user => 
            user.username === username && user.password === password
        );
    }

    // 处理登录
    function handleLogin(username, password) {
        // 显示加载状态
        loginButton.classList.add('loading');
        loginButton.textContent = '登录中...';
        loginButton.disabled = true;

        // 模拟网络请求延迟
        setTimeout(() => {
            const user = authenticateUser(username, password);
            
            loginButton.classList.remove('loading');
            loginButton.textContent = '登录';
            loginButton.disabled = false;

            if (user) {
                showMessage(`欢迎回来，${user.name}！`, 'success');
                
                // 如果选择记住我，保存到localStorage
                if (rememberCheckbox.checked) {
                    localStorage.setItem('rememberedUser', username);
                } else {
                    localStorage.removeItem('rememberedUser');
                }

                // 模拟跳转到主页
                setTimeout(() => {
                    showMessage('正在跳转到主页...', 'success');
                }, 1500);
                
            } else {
                showMessage('用户名或密码错误', 'error');
                passwordInput.value = '';
                passwordInput.focus();
            }
        }, 1500);
    }

    // 表单提交事件
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        if (validateForm()) {
            const username = usernameInput.value.trim();
            const password = passwordInput.value.trim();
            handleLogin(username, password);
        }
    });

    // 社交登录按钮事件
    document.querySelector('.social-button.google').addEventListener('click', function() {
        showMessage('Google登录功能开发中...', 'error');
    });

    document.querySelector('.social-button.github').addEventListener('click', function() {
        showMessage('GitHub登录功能开发中...', 'error');
    });

    // 忘记密码链接
    document.querySelector('.forgot-password').addEventListener('click', function(e) {
        e.preventDefault();
        showMessage('忘记密码功能开发中...', 'error');
    });

    // 注册链接
    document.querySelector('.signup-link a').addEventListener('click', function(e) {
        e.preventDefault();
        showMessage('注册功能开发中...', 'error');
    });

    // 输入框失焦验证
    usernameInput.addEventListener('blur', function() {
        if (!this.value.trim()) {
            this.style.borderColor = '#ef4444';
        } else {
            this.style.borderColor = '#e5e7eb';
        }
    });

    passwordInput.addEventListener('blur', function() {
        if (!this.value.trim()) {
            this.style.borderColor = '#ef4444';
        } else if (this.value.length < 6) {
            this.style.borderColor = '#f59e0b';
        } else {
            this.style.borderColor = '#e5e7eb';
        }
    });

    // 输入框聚焦时重置边框颜色
    [usernameInput, passwordInput].forEach(input => {
        input.addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
        });
    });

    // 页面加载时检查是否有记住的用户
    const rememberedUser = localStorage.getItem('rememberedUser');
    if (rememberedUser) {
        usernameInput.value = rememberedUser;
        rememberCheckbox.checked = true;
        passwordInput.focus();
    } else {
        usernameInput.focus();
    }

    // 键盘快捷键支持
    document.addEventListener('keydown', function(e) {
        // Enter键提交表单
        if (e.key === 'Enter' && (e.target === usernameInput || e.target === passwordInput)) {
            loginForm.dispatchEvent(new Event('submit'));
        }
    });

    // 显示欢迎消息
    setTimeout(() => {
        showMessage('欢迎使用登录系统！测试账户：admin/123456', 'success');
    }, 1000);
});