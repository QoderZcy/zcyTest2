package com.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存储空间信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageInfo {
    
    /**
     * 已使用空间(字节)
     */
    private Long usedSpace;
    
    /**
     * 已使用空间(可读格式)
     */
    private String usedSpaceReadable;
    
    /**
     * 总空间(字节)
     */
    private Long totalSpace;
    
    /**
     * 总空间(可读格式)
     */
    private String totalSpaceReadable;
    
    /**
     * 剩余空间(字节)
     */
    private Long freeSpace;
    
    /**
     * 剩余空间(可读格式)
     */
    private String freeSpaceReadable;
    
    /**
     * 使用百分比
     */
    private Double usagePercentage;
    
    /**
     * 文件总数
     */
    private Long totalFiles;
}
