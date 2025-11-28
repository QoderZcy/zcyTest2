package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 便签数据传输对象
 * 用于在控制器和视图之间传输便签数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    
    /**
     * 便签ID
     */
    private Long id;
    
    /**
     * 便签标题
     */
    private String title;
    
    /**
     * Markdown原始内容
     */
    private String content;
    
    /**
     * 渲染后的HTML内容
     */
    private String htmlContent;
    
    /**
     * 内容摘要（前100字符）
     */
    private String contentPreview;
    
    /**
     * 格式化的创建时间
     */
    private String createdTime;
    
    /**
     * 格式化的修改时间
     */
    private String updatedTime;
}
