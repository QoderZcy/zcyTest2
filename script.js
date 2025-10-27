// 表单验证规则
const validationRules = {
    username: {
        required: true,
        minLength: 3,
        maxLength: 20,
        pattern: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
        message: {
            required: '请输入用户名',
            minLength: '用户名至少3个字符',
            maxLength: '用户名不能超过20个字符',
            pattern: '用户名只能包含字母、数字、下划线和中文'
        }
    },
    password: {
        required: true,
        minLength: 6,
        maxLength: 50,
        pattern: /^(?=.*[a-zA-Z])(?=.*\d).+$/,
        message: {
            required: '请输入密码',
            minLength: '密码至少6个字符',
            maxLength: '密码不能超过50个字符',
            pattern: '密码必须包含字母和数字'
        }
    }
};

// DOM元素
const loginForm = document.getElementById('loginForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const loginBtn = document.getElementById('loginBtn');
const btnText = document.getElementById('btnText');
const loadingSpinner = document.getElementById('loadingSpinner');
const successModal = document.getElementById('successModal');
const rememberMeCheckbox = document.getElementById('rememberMe');

// 验证单个字段
function validateField(fieldName, value) {
    const rules = validationRules[fieldName];
    const errors = [];

    if (rules.required && !value.trim()) {
        errors.push(rules.message.required);
    } else if (value.trim()) {
        if (rules.minLength && value.length < rules.minLength) {
            errors.push(rules.message.minLength);
        }
        if (rules.maxLength && value.length > rules.maxLength) {
            errors.push(rules.message.maxLength);
        }
        if (rules.pattern && !rules.pattern.test(value)) {
            errors.push(rules.message.pattern);
        }
    }

    return errors;
}

// 显示错误信息
function showError(fieldName, errors) {
    const input = document.getElementById(fieldName);
    const errorElement = document.getElementById(fieldName + 'Error');
    
    if (errors.length > 0) {
        input.classList.add('error');
        errorElement.textContent = errors[0];
        errorElement.classList.add('show');
    } else {
        input.classList.remove('error');
        errorElement.textContent = '';
        errorElement.classList.remove('show');
    }
}

// 实时验证
function setupRealTimeValidation() {
    usernameInput.addEventListener('blur', function() {
        const errors = validateField('username', this.value);
        showError('username', errors);
    });

    passwordInput.addEventListener('blur', function() {
        const errors = validateField('password', this.value);
        showError('password', errors);
    });

    // 输入时清除错误状态
    usernameInput.addEventListener('input', function() {
        if (this.classList.contains('error')) {
            this.classList.remove('error');
            const errorElement = document.getElementById('usernameError');
            errorElement.classList.remove('show');
        }
    });

    passwordInput.addEventListener('input', function() {
        if (this.classList.contains('error')) {
            this.classList.remove('error');
            const errorElement = document.getElementById('passwordError');
            errorElement.classList.remove('show');
        }
    });
}

// 验证整个表单
function validateForm() {
    const username = usernameInput.value;
    const password = passwordInput.value;
    
    const usernameErrors = validateField('username', username);
    const passwordErrors = validateField('password', password);
    
    showError('username', usernameErrors);
    showError('password', passwordErrors);
    
    return usernameErrors.length === 0 && passwordErrors.length === 0;
}

// 模拟登录API调用
async function loginAPI(username, password, rememberMe) {
    // 模拟网络延迟
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    // 模拟登录验证（实际项目中应该调用真实的后端API）
    const validCredentials = [
        { username: 'admin', password: 'admin123' },
        { username: 'user', password: 'user123' },
        { username: 'test', password: 'test123' },
        { username: '测试用户', password: 'test123' }
    ];
    
    const isValid = validCredentials.some(cred => 
        cred.username === username && cred.password === password
    );
    
    if (isValid) {
        // 登录成功，保存登录状态
        const loginData = {
            username: username,
            loginTime: new Date().toISOString(),
            rememberMe: rememberMe
        };
        
        if (rememberMe) {
            localStorage.setItem('loginData', JSON.stringify(loginData));
        } else {
            sessionStorage.setItem('loginData', JSON.stringify(loginData));
        }
        
        return { success: true, message: '登录成功' };
    } else {
        return { success: false, message: '用户名或密码错误' };
    }
}

// 显示加载状态
function showLoading() {
    loginBtn.classList.add('loading');
    btnText.textContent = '登录中...';
    loginBtn.disabled = true;
}

// 隐藏加载状态
function hideLoading() {
    loginBtn.classList.remove('loading');
    btnText.textContent = '登录';
    loginBtn.disabled = false;
}

// 显示成功模态框
function showSuccessModal() {
    successModal.style.display = 'block';
    
    // 3秒后自动关闭并跳转
    setTimeout(() => {
        successModal.style.display = 'none';
        // 这里可以添加页面跳转逻辑
        console.log('跳转到首页...');
        // window.location.href = '/dashboard.html';
    }, 3000);
}

// 处理表单提交
async function handleFormSubmit(event) {
    event.preventDefault();
    
    // 验证表单
    if (!validateForm()) {
        return;
    }
    
    const username = usernameInput.value.trim();
    const password = passwordInput.value;
    const rememberMe = rememberMeCheckbox.checked;
    
    try {
        showLoading();
        
        const result = await loginAPI(username, password, rememberMe);
        
        hideLoading();
        
        if (result.success) {
            showSuccessModal();
        } else {
            // 显示登录失败错误
            showError('password', [result.message]);
        }
    } catch (error) {
        hideLoading();
        showError('password', ['登录失败，请稍后重试']);
        console.error('登录错误:', error);
    }
}

// 检查是否有保存的登录信息
function checkSavedLogin() {
    const savedLogin = localStorage.getItem('loginData') || sessionStorage.getItem('loginData');
    
    if (savedLogin) {
        try {
            const loginData = JSON.parse(savedLogin);
            usernameInput.value = loginData.username;
            rememberMeCheckbox.checked = loginData.rememberMe;
        } catch (error) {
            console.error('解析保存的登录信息失败:', error);
        }
    }
}

// 社交登录按钮处理
function setupSocialLogin() {
    const wechatBtn = document.querySelector('.wechat-btn');
    const qqBtn = document.querySelector('.qq-btn');
    
    wechatBtn.addEventListener('click', function() {
        alert('微信登录功能开发中...');
    });
    
    qqBtn.addEventListener('click', function() {
        alert('QQ登录功能开发中...');
    });
}

// 忘记密码处理
function setupForgotPassword() {
    const forgotPasswordLink = document.querySelector('.forgot-password');
    forgotPasswordLink.addEventListener('click', function(e) {
        e.preventDefault();
        alert('找回密码功能开发中...');
    });
}

// 注册链接处理
function setupRegisterLink() {
    const registerLink = document.querySelector('.register-link a');
    registerLink.addEventListener('click', function(e) {
        e.preventDefault();
        alert('注册功能开发中...');
    });
}

// 键盘事件处理
function setupKeyboardEvents() {
    // Enter键提交表单
    document.addEventListener('keydown', function(event) {
        if (event.key === 'Enter' && document.activeElement.tagName !== 'BUTTON') {
            event.preventDefault();
            loginForm.dispatchEvent(new Event('submit'));
        }
    });
}

// 点击模态框外部关闭
function setupModalClose() {
    successModal.addEventListener('click', function(event) {
        if (event.target === successModal) {
            successModal.style.display = 'none';
        }
    });
}

// 初始化应用
function initApp() {
    // 检查保存的登录信息
    checkSavedLogin();
    
    // 设置实时验证
    setupRealTimeValidation();
    
    // 设置表单提交
    loginForm.addEventListener('submit', handleFormSubmit);
    
    // 设置社交登录
    setupSocialLogin();
    
    // 设置忘记密码
    setupForgotPassword();
    
    // 设置注册链接
    setupRegisterLink();
    
    // 设置键盘事件
    setupKeyboardEvents();
    
    // 设置模态框关闭
    setupModalClose();
    
    // 自动聚焦到用户名输入框
    setTimeout(() => {
        usernameInput.focus();
    }, 500);
    
    console.log('登录页面初始化完成');
    console.log('测试账号: admin/admin123, user/user123, test/test123, 测试用户/test123');
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', initApp);