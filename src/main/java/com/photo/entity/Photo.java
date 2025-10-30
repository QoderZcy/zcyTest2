package com.photo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 照片实体类
 * 用于存储照片的元数据信息
 */
@Entity
@Table(name = "photos", indexes = {
    @Index(name = "idx_original_filename", columnList = "originalFilename"),
    @Index(name = "idx_created_at", columnList = "createdAt"),
    @Index(name = "idx_user_id", columnList = "userId")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 原始文件名
     */
    @Column(nullable = false, length = 500)
    private String originalFilename;
    
    /**
     * 存储文件名(UUID生成)
     */
    @Column(nullable = false, unique = true, length = 100)
    private String storedFilename;
    
    /**
     * 文件路径
     */
    @Column(nullable = false, length = 1000)
    private String filePath;
    
    /**
     * 缩略图路径
     */
    @Column(length = 1000)
    private String thumbnailPath;
    
    /**
     * 文件大小(字节)
     */
    @Column(nullable = false)
    private Long fileSize;
    
    /**
     * 文件MIME类型
     */
    @Column(nullable = false, length = 100)
    private String contentType;
    
    /**
     * 文件扩展名
     */
    @Column(nullable = false, length = 20)
    private String extension;
    
    /**
     * 图片宽度
     */
    private Integer width;
    
    /**
     * 图片高度
     */
    private Integer height;
    
    /**
     * 文件MD5值(用于去重)
     */
    @Column(length = 32, unique = true)
    private String md5;
    
    /**
     * 上传用户ID
     */
    @Column(nullable = false)
    private String userId;
    
    /**
     * 访问次数
     */
    @Column(nullable = false)
    @Builder.Default
    private Long accessCount = 0L;
    
    /**
     * 下载次数
     */
    @Column(nullable = false)
    @Builder.Default
    private Long downloadCount = 0L;
    
    /**
     * 是否公开
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = true;
    
    /**
     * 是否已删除(软删除)
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;
    
    /**
     * 备注信息
     */
    @Column(length = 1000)
    private String description;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessedAt;
    
    /**
     * IP地址
     */
    @Column(length = 50)
    private String ipAddress;
    
    /**
     * 增加访问次数
     */
    public void incrementAccessCount() {
        this.accessCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }
    
    /**
     * 增加下载次数
     */
    public void incrementDownloadCount() {
        this.downloadCount++;
    }
}
