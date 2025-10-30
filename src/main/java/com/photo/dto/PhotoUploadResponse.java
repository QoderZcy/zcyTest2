package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 照片上传响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUploadResponse {
    
    /**
     * 照片ID
     */
    private Long id;
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 存储文件名
     */
    private String storedFilename;
    
    /**
     * 文件大小(字节)
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
     * 上传时间
     */
    private LocalDateTime uploadedAt;
    
    /**
     * MD5值
     */
    private String md5;
}
