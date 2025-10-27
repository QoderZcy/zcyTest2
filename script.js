// 页面导航功能
document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const navToggle = document.getElementById('navToggle');
    const navMenu = document.getElementById('navMenu');
    const navLinks = document.querySelectorAll('.nav-link');
    const pageSections = document.querySelectorAll('.page-section');

    // 移动端菜单切换
    navToggle.addEventListener('click', function() {
        navMenu.classList.toggle('active');
        navToggle.classList.toggle('active');
    });

    // 导航链接点击事件
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 获取目标页面
            const targetPage = this.getAttribute('data-page');
            
            // 移除所有活动状态
            navLinks.forEach(navLink => navLink.classList.remove('active'));
            pageSections.forEach(section => section.classList.remove('active'));
            
            // 添加当前活动状态
            this.classList.add('active');
            document.getElementById(targetPage).classList.add('active');
            
            // 关闭移动端菜单
            navMenu.classList.remove('active');
            navToggle.classList.remove('active');
            
            // 滚动到顶部
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
            
            // 更新URL hash（不触发页面跳转）
            history.pushState(null, null, `#${targetPage}`);
        });
    });

    // 处理浏览器前进后退
    window.addEventListener('popstate', function() {
        handleHashChange();
    });

    // 处理hash变化
    function handleHashChange() {
        let hash = window.location.hash.substring(1) || 'home';
        
        // 查找对应的导航链接
        const targetLink = document.querySelector(`.nav-link[data-page="${hash}"]`);
        const targetSection = document.getElementById(hash);
        
        if (targetLink && targetSection) {
            // 移除所有活动状态
            navLinks.forEach(navLink => navLink.classList.remove('active'));
            pageSections.forEach(section => section.classList.remove('active'));
            
            // 添加当前活动状态
            targetLink.classList.add('active');
            targetSection.classList.add('active');
        }
    }

    // 页面加载时检查hash
    handleHashChange();

    // 点击页面其他区域关闭移动端菜单
    document.addEventListener('click', function(e) {
        const isClickInsideNav = navMenu.contains(e.target) || navToggle.contains(e.target);
        if (!isClickInsideNav && navMenu.classList.contains('active')) {
            navMenu.classList.remove('active');
            navToggle.classList.remove('active');
        }
    });

    // 表单提交处理
    const contactForm = document.querySelector('.contact-form form');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // 获取表单数据
            const formData = new FormData(this);
            const name = this.querySelector('input[type="text"]').value;
            const email = this.querySelector('input[type="email"]').value;
            const message = this.querySelector('textarea').value;
            
            // 这里可以添加实际的表单提交逻辑
            console.log('表单数据:', { name, email, message });
            
            // 显示成功消息
            alert('感谢您的留言！我们会尽快与您联系。');
            
            // 重置表单
            this.reset();
        });
    }

    // 产品按钮点击事件
    const productButtons = document.querySelectorAll('.product-card .btn');
    productButtons.forEach(button => {
        button.addEventListener('click', function() {
            const productName = this.parentElement.querySelector('h3').textContent;
            alert(`您对 ${productName} 感兴趣！我们会尽快为您提供更多信息。`);
        });
    });

    // 导航栏滚动效果
    let lastScroll = 0;
    window.addEventListener('scroll', function() {
        const navbar = document.querySelector('.navbar');
        const currentScroll = window.pageYOffset;

        if (currentScroll > 100) {
            navbar.style.boxShadow = '0 5px 20px rgba(0, 0, 0, 0.2)';
        } else {
            navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
        }

        lastScroll = currentScroll;
    });

    // 添加平滑滚动支持
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href === '#' || href === '#home' || href === '#about' || 
                href === '#services' || href === '#products' || href === '#contact') {
                // 这些由导航系统处理
                return;
            }
            
            e.preventDefault();
            const target = document.querySelector(href);
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // 键盘导航支持
    document.addEventListener('keydown', function(e) {
        // ESC键关闭移动端菜单
        if (e.key === 'Escape' && navMenu.classList.contains('active')) {
            navMenu.classList.remove('active');
            navToggle.classList.remove('active');
        }
    });

    // 添加页面加载动画
    setTimeout(() => {
        document.body.style.opacity = '1';
    }, 100);
});

// 初始化页面透明度（用于淡入效果）
document.body.style.opacity = '0';
document.body.style.transition = 'opacity 0.3s ease';
