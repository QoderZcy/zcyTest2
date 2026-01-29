package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客数据传输对象
 * 用于前后端数据传输，包含原始内容和HTML渲染内容
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    
    /**
     * 博客ID
     */
    private Long id;
    
    /**
     * 博客标题
     */
    private String title;
    
    /**
     * 博客作者
     */
    private String author;
    
    /**
     * 博客原始内容（Markdown格式）
     */
    private String content;
    
    /**
     * 博客HTML渲染内容
     */
    private String htmlContent;
    
    /**
     * 博客内容摘要（用于列表页显示）
     */
    private String contentPreview;
    
    /**
     * 博客分类
     */
    private String category;
    
    /**
     * 博客标签（多个标签用逗号分隔）
     */
    private String tags;
    
    /**
     * 创建时间（格式化字符串）
     */
    private String createdTime;
    
    /**
     * 最后修改时间（格式化字符串）
     */
    private String updatedTime;
}
