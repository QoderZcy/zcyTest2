/**
 * 便签编辑器 - JavaScript文件
 * 处理编辑器交互逻辑、实时预览和快捷键
 */

let previewTimeout = null;

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    const contentTextarea = document.getElementById('content');
    const previewDiv = document.getElementById('preview');
    
    if (contentTextarea && previewDiv) {
        // 初始化预览
        updatePreview(contentTextarea.value);
        
        // 监听内容变化
        contentTextarea.addEventListener('input', function() {
            // 使用防抖优化性能
            clearTimeout(previewTimeout);
            previewTimeout = setTimeout(() => {
                updatePreview(this.value);
            }, 300);
        });
        
        // 支持Tab键缩进
        contentTextarea.addEventListener('keydown', function(e) {
            if (e.key === 'Tab') {
                e.preventDefault();
                const start = this.selectionStart;
                const end = this.selectionEnd;
                const value = this.value;
                
                this.value = value.substring(0, start) + '    ' + value.substring(end);
                this.selectionStart = this.selectionEnd = start + 4;
            }
        });
    }
    
    // 注册快捷键
    registerShortcuts();
});

/**
 * 更新Markdown预览
 */
function updatePreview(markdown) {
    const previewDiv = document.getElementById('preview');
    
    if (!markdown || markdown.trim() === '') {
        previewDiv.innerHTML = '<p class="preview-placeholder">开始输入内容以查看预览...</p>';
        return;
    }
    
    // 调用后端API渲染Markdown
    fetch('/api/preview', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain; charset=UTF-8'
        },
        body: markdown
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('预览渲染失败');
        }
        return response.text();
    })
    .then(html => {
        previewDiv.innerHTML = html;
        
        // 为预览内容添加样式类
        previewDiv.className = 'preview-content markdown-body';
    })
    .catch(error => {
        console.error('预览错误:', error);
        previewDiv.innerHTML = '<p style="color: var(--color-danger);">预览渲染失败，请检查Markdown格式</p>';
    });
}

/**
 * 注册快捷键
 */
function registerShortcuts() {
    document.addEventListener('keydown', function(e) {
        // Ctrl+S 或 Cmd+S 保存
        if ((e.ctrlKey || e.metaKey) && e.key === 's') {
            e.preventDefault();
            const form = document.getElementById('noteForm');
            if (form) {
                console.log('快捷键保存');
                form.submit();
            }
        }
        
        // Ctrl+Esc 取消
        if (e.ctrlKey && e.key === 'Escape') {
            e.preventDefault();
            history.back();
        }
        
        // Ctrl+B 加粗
        if ((e.ctrlKey || e.metaKey) && e.key === 'b') {
            e.preventDefault();
            insertMarkdown('**', '**');
        }
        
        // Ctrl+I 斜体
        if ((e.ctrlKey || e.metaKey) && e.key === 'i') {
            e.preventDefault();
            insertMarkdown('*', '*');
        }
        
        // Ctrl+K 插入链接
        if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
            e.preventDefault();
            insertMarkdown('[', '](url)');
        }
    });
}

/**
 * 在光标位置插入Markdown标记
 */
function insertMarkdown(before, after) {
    const textarea = document.getElementById('content');
    if (!textarea) return;
    
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;
    const selectedText = textarea.value.substring(start, end);
    const replacement = before + (selectedText || '文本') + after;
    
    textarea.value = textarea.value.substring(0, start) + replacement + textarea.value.substring(end);
    
    // 重新定位光标
    if (selectedText) {
        textarea.selectionStart = start;
        textarea.selectionEnd = start + replacement.length;
    } else {
        textarea.selectionStart = textarea.selectionEnd = start + before.length;
    }
    
    textarea.focus();
    
    // 触发预览更新
    updatePreview(textarea.value);
}

/**
 * 插入标题
 */
function insertHeading(level) {
    const prefix = '#'.repeat(level) + ' ';
    const textarea = document.getElementById('content');
    if (!textarea) return;
    
    const start = textarea.selectionStart;
    const value = textarea.value;
    
    // 查找当前行的开始位置
    let lineStart = start;
    while (lineStart > 0 && value[lineStart - 1] !== '\n') {
        lineStart--;
    }
    
    textarea.value = value.substring(0, lineStart) + prefix + value.substring(lineStart);
    textarea.selectionStart = textarea.selectionEnd = start + prefix.length;
    textarea.focus();
    
    updatePreview(textarea.value);
}

/**
 * 插入代码块
 */
function insertCodeBlock() {
    insertMarkdown('\n```\n', '\n```\n');
}

/**
 * 插入列表
 */
function insertList() {
    const textarea = document.getElementById('content');
    if (!textarea) return;
    
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;
    const selectedText = textarea.value.substring(start, end);
    
    let replacement;
    if (selectedText) {
        // 将选中的文本转换为列表
        const lines = selectedText.split('\n');
        replacement = lines.map(line => '- ' + line).join('\n');
    } else {
        replacement = '- ';
    }
    
    textarea.value = textarea.value.substring(0, start) + replacement + textarea.value.substring(end);
    textarea.selectionStart = textarea.selectionEnd = start + replacement.length;
    textarea.focus();
    
    updatePreview(textarea.value);
}
