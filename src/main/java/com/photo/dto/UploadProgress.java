package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传进度DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadProgress {
    
    /**
     * 已上传字节数
     */
    private Long bytesRead;
    
    /**
     * 总字节数
     */
    private Long totalBytes;
    
    /**
     * 上传百分比
     */
    private Integer percentage;
    
    /**
     * 当前上传的文件索引
     */
    private Integer currentFileIndex;
    
    /**
     * 总文件数
     */
    private Integer totalFiles;
    
    /**
     * 状态(uploading, completed, failed)
     */
    private String status;
    
    /**
     * 消息
     */
    private String message;
}
