package com.photo.controller;

import com.photo.dto.BlogDTO;
import com.photo.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 博客控制器
 * 处理博客相关的所有HTTP请求
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    
    private final BlogService blogService;
    
    /**
     * 博客列表页面
     * GET /blogs
     */
    @GetMapping
    public String listBlogs(Model model,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) String author) {
        log.info("访问博客列表页面，分类: {}, 作者: {}", category, author);
        
        List<BlogDTO> blogs;
        if (category != null && !category.isEmpty()) {
            blogs = blogService.getBlogsByCategory(category);
            model.addAttribute("filterType", "分类");
            model.addAttribute("filterValue", category);
        } else if (author != null && !author.isEmpty()) {
            blogs = blogService.getBlogsByAuthor(author);
            model.addAttribute("filterType", "作者");
            model.addAttribute("filterValue", author);
        } else {
            blogs = blogService.getAllBlogs();
        }
        
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }
    
    /**
     * 显示新建博客表单
     * GET /blogs/new
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        log.info("显示新建博客表单");
        model.addAttribute("blog", new BlogDTO());
        model.addAttribute("isNew", true);
        return "blog/edit";
    }
    
    /**
     * 创建博客
     * POST /blogs
     */
    @PostMapping
    public String createBlog(@RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String content,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String tags,
                            RedirectAttributes redirectAttributes) {
        log.info("处理创建博客请求，标题: {}, 作者: {}", title, author);
        
        try {
            BlogDTO blog = blogService.createBlog(title, author, content, category, tags);
            redirectAttributes.addFlashAttribute("message", "博客创建成功");
            return "redirect:/blogs/" + blog.getId();
        } catch (Exception e) {
            log.error("创建博客失败", e);
            redirectAttributes.addFlashAttribute("error", "创建博客失败: " + e.getMessage());
            return "redirect:/blogs/new";
        }
    }
    
    /**
     * 博客详情页面
     * GET /blogs/{id}
     */
    @GetMapping("/{id}")
    public String showBlog(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("访问博客详情页面，ID: {}", id);
        
        return blogService.getBlogById(id)
                .map(blog -> {
                    model.addAttribute("blog", blog);
                    return "blog/detail";
                })
                .orElseGet(() -> {
                    log.warn("博客不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "博客不存在");
                    return "redirect:/blogs";
                });
    }
    
    /**
     * 显示编辑博客表单
     * GET /blogs/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("显示编辑博客表单，ID: {}", id);
        
        return blogService.getBlogById(id)
                .map(blog -> {
                    model.addAttribute("blog", blog);
                    model.addAttribute("isNew", false);
                    return "blog/edit";
                })
                .orElseGet(() -> {
                    log.warn("博客不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "博客不存在");
                    return "redirect:/blogs";
                });
    }
    
    /**
     * 更新博客
     * POST /blogs/{id}
     */
    @PostMapping("/{id}")
    public String updateBlog(@PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String content,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String tags,
                            RedirectAttributes redirectAttributes) {
        log.info("处理更新博客请求，ID: {}", id);
        
        return blogService.updateBlog(id, title, author, content, category, tags)
                .map(blog -> {
                    redirectAttributes.addFlashAttribute("message", "博客更新成功");
                    return "redirect:/blogs/" + blog.getId();
                })
                .orElseGet(() -> {
                    log.warn("博客不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "博客不存在");
                    return "redirect:/blogs";
                });
    }
    
    /**
     * 删除博客
     * POST /blogs/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("处理删除博客请求，ID: {}", id);
        
        if (blogService.deleteBlog(id)) {
            redirectAttributes.addFlashAttribute("message", "博客删除成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "博客不存在");
        }
        
        return "redirect:/blogs";
    }
}
