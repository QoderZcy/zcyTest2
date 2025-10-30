package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 照片信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {
    
    /**
     * 照片ID
     */
    private Long id;
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 文件大小
     */
    private Long fileSize;
    
    /**
     * 文件大小(可读格式)
     */
    private String fileSizeReadable;
    
    /**
     * 文件类型
     */
    private String contentType;
    
    /**
     * 文件URL
     */
    private String url;
    
    /**
     * 缩略图URL
     */
    private String thumbnailUrl;
    
    /**
     * 下载URL
     */
    private String downloadUrl;
    
    /**
     * 图片宽度
     */
    private Integer width;
    
    /**
     * 图片高度
     */
    private Integer height;
    
    /**
     * 访问次数
     */
    private Long accessCount;
    
    /**
     * 下载次数
     */
    private Long downloadCount;
    
    /**
     * 是否公开
     */
    private Boolean isPublic;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessedAt;
}
