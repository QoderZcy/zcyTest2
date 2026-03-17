package com.photo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 文件存储配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {
    
    /**
     * 文件存储根目录
     */
    private String basePath = "./uploads";
    
    /**
     * 临时文件目录
     */
    private String tempPath = "./uploads/temp";
    
    /**
     * 缩略图目录
     */
    private String thumbnailPath = "./uploads/thumbnails";
    
    /**
     * 允许的文件类型
     */
    private List<String> allowedTypes;
    
    /**
     * 允许的文件扩展名
     */
    private List<String> allowedExtensions;
    
    /**
     * 最大文件大小(字节)
     */
    private Long maxFileSize = 10485760L; // 10MB
    
    /**
     * 最大单次上传文件数
     */
    private Integer maxFilesPerUpload = 10;
    
    /**
     * 缩略图配置
     */
    private ThumbnailConfig thumbnail = new ThumbnailConfig();
    
    /**
     * 图片压缩配置
     */
    private CompressionConfig compression = new CompressionConfig();
    
    /**
     * 存储容量限制(字节)
     */
    private Long maxStorageSize = 10737418240L; // 10GB
    
    /**
     * 定期清理配置
     */
    private CleanupConfig cleanup = new CleanupConfig();
    
    @Data
    public static class ThumbnailConfig {
        private Integer width = 200;
        private Integer height = 200;
        private Double quality = 0.8;
    }
    
    @Data
    public static class CompressionConfig {
        private Boolean enabled = true;
        private Double quality = 0.85;
        private Integer maxWidth = 1920;
        private Integer maxHeight = 1080;
    }
    
    @Data
    public static class CleanupConfig {
        private Boolean enabled = true;
        private Integer daysToKeep = 30;
        private String cron = "0 0 2 * * ?";
    }
}
