package com.photo.service;

import com.photo.dto.BlogDTO;
import com.photo.entity.Blog;
import com.photo.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 博客业务服务类
 * 提供博客的增删改查业务逻辑和Markdown处理
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {
    
    private final BlogRepository blogRepository;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 获取所有博客列表
     * @return 博客DTO列表
     */
    public List<BlogDTO> getAllBlogs() {
        log.info("获取所有博客列表");
        List<Blog> blogs = blogRepository.findAllByOrderByCreatedTimeDesc();
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据ID获取博客
     * @param id 博客ID
     * @return 博客DTO
     */
    public Optional<BlogDTO> getBlogById(Long id) {
        log.info("获取博客详情，ID: {}", id);
        return blogRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * 根据分类获取博客列表
     * @param category 分类名称
     * @return 博客DTO列表
     */
    public List<BlogDTO> getBlogsByCategory(String category) {
        log.info("根据分类获取博客列表，分类: {}", category);
        List<Blog> blogs = blogRepository.findByCategoryOrderByCreatedTimeDesc(category);
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据作者获取博客列表
     * @param author 作者名称
     * @return 博客DTO列表
     */
    public List<BlogDTO> getBlogsByAuthor(String author) {
        log.info("根据作者获取博客列表，作者: {}", author);
        List<Blog> blogs = blogRepository.findByAuthorOrderByCreatedTimeDesc(author);
        return blogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 创建新博客
     * @param title 标题
     * @param author 作者
     * @param content 内容
     * @param category 分类
     * @param tags 标签
     * @return 创建的博客DTO
     */
    @Transactional
    public BlogDTO createBlog(String title, String author, String content, String category, String tags) {
        log.info("创建新博客，标题: {}, 作者: {}", title, author);
        
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setAuthor(author);
        blog.setContent(content);
        blog.setCategory(category);
        blog.setTags(tags);
        
        Blog savedBlog = blogRepository.save(blog);
        log.info("博客创建成功，ID: {}", savedBlog.getId());
        
        return convertToDTO(savedBlog);
    }
    
    /**
     * 更新博客
     * @param id 博客ID
     * @param title 新标题
     * @param author 新作者
     * @param content 新内容
     * @param category 新分类
     * @param tags 新标签
     * @return 更新后的博客DTO
     */
    @Transactional
    public Optional<BlogDTO> updateBlog(Long id, String title, String author, String content, String category, String tags) {
        log.info("更新博客，ID: {}", id);
        
        return blogRepository.findById(id)
                .map(blog -> {
                    blog.setTitle(title);
                    blog.setAuthor(author);
                    blog.setContent(content);
                    blog.setCategory(category);
                    blog.setTags(tags);
                    Blog updatedBlog = blogRepository.save(blog);
                    log.info("博客更新成功，ID: {}", updatedBlog.getId());
                    return convertToDTO(updatedBlog);
                });
    }
    
    /**
     * 删除博客
     * @param id 博客ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteBlog(Long id) {
        log.info("删除博客，ID: {}", id);
        
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            log.info("博客删除成功，ID: {}", id);
            return true;
        }
        
        log.warn("博客不存在，无法删除，ID: {}", id);
        return false;
    }
    
    /**
     * 将Markdown文本渲染为HTML
     * @param markdown Markdown文本
     * @return HTML文本
     */
    public String renderMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        
        try {
            org.commonmark.node.Node document = markdownParser.parse(markdown);
            return htmlRenderer.render(document);
        } catch (Exception e) {
            log.error("Markdown渲染失败", e);
            return markdown;
        }
    }
    
    /**
     * 生成内容摘要
     * @param content 完整内容
     * @return 摘要文本（前150字符）
     */
    private String generatePreview(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        // 移除Markdown标记符号
        String plainText = content.replaceAll("[#*`\\[\\]()\\-_>]", "").trim();
        
        if (plainText.length() <= 150) {
            return plainText;
        }
        
        return plainText.substring(0, 150) + "...";
    }
    
    /**
     * 将Blog实体转换为BlogDTO
     * @param blog Blog实体
     * @return BlogDTO对象
     */
    private BlogDTO convertToDTO(Blog blog) {
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .author(blog.getAuthor())
                .content(blog.getContent())
                .htmlContent(renderMarkdown(blog.getContent()))
                .contentPreview(generatePreview(blog.getContent()))
                .category(blog.getCategory())
                .tags(blog.getTags())
                .createdTime(blog.getCreatedTime().format(DATE_TIME_FORMATTER))
                .updatedTime(blog.getUpdatedTime().format(DATE_TIME_FORMATTER))
                .build();
    }
}
