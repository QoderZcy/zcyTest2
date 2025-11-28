/**
 * 便签管理系统 - 主JavaScript文件
 * 处理通用交互逻辑
 */

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    console.log('便签管理系统已加载');
    
    // 自动隐藏消息提示
    hideMessagesAutomatically();
    
    // 添加按钮点击动画
    addButtonAnimations();
});

/**
 * 自动隐藏消息提示
 */
function hideMessagesAutomatically() {
    const messages = document.querySelectorAll('.message');
    messages.forEach(message => {
        setTimeout(() => {
            message.style.opacity = '0';
            message.style.transform = 'translateY(-20px)';
            setTimeout(() => {
                message.remove();
            }, 500);
        }, 5000);
    });
}

/**
 * 添加按钮点击动画
 */
function addButtonAnimations() {
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            // 创建波纹效果
            const ripple = document.createElement('span');
            ripple.className = 'ripple';
            ripple.style.left = e.offsetX + 'px';
            ripple.style.top = e.offsetY + 'px';
            this.appendChild(ripple);
            
            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
}

/**
 * 确认删除对话框
 */
function confirmDelete(message) {
    return confirm(message || '确定要删除吗？此操作不可撤销。');
}

/**
 * 显示加载动画
 */
function showLoading() {
    const loader = document.createElement('div');
    loader.className = 'loader';
    loader.innerHTML = '<div class="spinner"></div>';
    document.body.appendChild(loader);
}

/**
 * 隐藏加载动画
 */
function hideLoading() {
    const loader = document.querySelector('.loader');
    if (loader) {
        loader.remove();
    }
}
