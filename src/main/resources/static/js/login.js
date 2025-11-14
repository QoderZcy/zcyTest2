/**
 * 登录页面JavaScript逻辑
 */

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    initializePage();
    setupFormValidation();
    setupFormSubmit();
});

/**
 * 初始化页面
 */
function initializePage() {
    // 获取并设置CSRF Token
    fetchCSRFToken();
    
    // 检查URL参数，显示相应提示
    const urlParams = new URLSearchParams(window.location.search);
    
    if (urlParams.has('error')) {
        showMessage('用户名或密码错误，请重试', 'error');
    } else if (urlParams.has('logout')) {
        showMessage('您已成功登出', 'success');
    } else if (urlParams.has('locked')) {
        showMessage('账户已被锁定，请联系管理员', 'error');
    } else if (urlParams.has('invalid')) {
        showMessage('请求参数错误', 'error');
    }
}

/**
 * 获取CSRF Token
 */
function fetchCSRFToken() {
    // 尝试从cookie中获取CSRF Token
    const csrfToken = getCookie('XSRF-TOKEN');
    if (csrfToken) {
        document.getElementById('csrfToken').value = csrfToken;
        return;
    }
    
    // 如果cookie中没有，尝试从meta标签获取
    const csrfMeta = document.querySelector('meta[name="_csrf"]');
    const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');
    
    if (csrfMeta) {
        document.getElementById('csrfToken').value = csrfMeta.content;
    } else {
        // 如果都没有，发送请求获取
        fetch('/login', {
            method: 'GET',
            credentials: 'include'
        }).then(() => {
            // 重新尝试从cookie获取
            const token = getCookie('XSRF-TOKEN');
            if (token) {
                document.getElementById('csrfToken').value = token;
            }
        }).catch(error => {
            console.error('获取CSRF Token失败:', error);
        });
    }
}

/**
 * 从Cookie中获取值
 */
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }
    return null;
}

/**
 * 设置表单验证
 */
function setupFormValidation() {
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    
    // 用户名验证
    usernameInput.addEventListener('blur', function() {
        validateUsername();
    });
    
    usernameInput.addEventListener('input', function() {
        clearError('usernameError');
    });
    
    // 密码验证
    passwordInput.addEventListener('blur', function() {
        validatePassword();
    });
    
    passwordInput.addEventListener('input', function() {
        clearError('passwordError');
    });
}

/**
 * 验证用户名
 */
function validateUsername() {
    const username = document.getElementById('username').value.trim();
    const errorElement = document.getElementById('usernameError');
    
    if (!username) {
        showError('usernameError', '请输入用户名');
        return false;
    }
    
    if (username.length < 3 || username.length > 20) {
        showError('usernameError', '用户名长度应为3-20个字符');
        return false;
    }
    
    const usernamePattern = /^[a-zA-Z0-9_]+$/;
    if (!usernamePattern.test(username)) {
        showError('usernameError', '用户名只能包含字母、数字和下划线');
        return false;
    }
    
    clearError('usernameError');
    return true;
}

/**
 * 验证密码
 */
function validatePassword() {
    const password = document.getElementById('password').value;
    const errorElement = document.getElementById('passwordError');
    
    if (!password) {
        showError('passwordError', '请输入密码');
        return false;
    }
    
    if (password.length < 6 || password.length > 20) {
        showError('passwordError', '密码长度应为6-20个字符');
        return false;
    }
    
    clearError('passwordError');
    return true;
}

/**
 * 显示错误信息
 */
function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

/**
 * 清除错误信息
 */
function clearError(elementId) {
    const errorElement = document.getElementById(elementId);
    errorElement.textContent = '';
    errorElement.style.display = 'none';
}

/**
 * 设置表单提交
 */
function setupFormSubmit() {
    const loginForm = document.getElementById('loginForm');
    
    loginForm.addEventListener('submit', function(event) {
        // 清除之前的消息
        hideMessage();
        
        // 验证表单
        const isUsernameValid = validateUsername();
        const isPasswordValid = validatePassword();
        
        if (!isUsernameValid || !isPasswordValid) {
            event.preventDefault();
            showMessage('请检查输入的信息', 'error');
            return false;
        }
        
        // 显示加载状态
        const submitButton = loginForm.querySelector('.login-btn');
        submitButton.classList.add('loading');
        submitButton.disabled = true;
        
        // 表单会正常提交，不需要阻止默认行为
        return true;
    });
}

/**
 * 显示提示信息
 */
function showMessage(message, type) {
    const messageBox = document.getElementById('messageBox');
    const messageText = document.getElementById('messageText');
    
    messageText.textContent = message;
    messageBox.className = 'message-box ' + type;
    messageBox.style.display = 'flex';
    
    // 5秒后自动隐藏成功消息
    if (type === 'success') {
        setTimeout(hideMessage, 5000);
    }
}

/**
 * 隐藏提示信息
 */
function hideMessage() {
    const messageBox = document.getElementById('messageBox');
    messageBox.style.display = 'none';
}

/**
 * 关闭提示信息（通过按钮）
 */
function closeMessage() {
    hideMessage();
}

/**
 * 防止XSS攻击 - 转义HTML特殊字符
 */
function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, function(m) { return map[m]; });
}
