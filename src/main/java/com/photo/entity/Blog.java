package com.photo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 博客实体类
 * 用于存储博客文章的基本信息，包括标题、作者、内容和时间戳
 */
@Entity
@Table(name = "blogs", indexes = {
    @Index(name = "idx_blog_created_time", columnList = "created_time"),
    @Index(name = "idx_blog_author", columnList = "author")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    
    /**
     * 博客唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 博客标题
     */
    @Column(nullable = false, length = 300)
    private String title;
    
    /**
     * 博客作者
     */
    @Column(nullable = false, length = 100)
    private String author;
    
    /**
     * 博客内容，支持Markdown格式
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    
    /**
     * 博客分类
     */
    @Column(length = 50)
    private String category;
    
    /**
     * 博客标签（多个标签用逗号分隔）
     */
    @Column(length = 200)
    private String tags;
    
    /**
     * 博客创建时间
     */
    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;
    
    /**
     * 博客最后修改时间
     */
    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
}
