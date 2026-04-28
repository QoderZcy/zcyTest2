package com.photo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String originalFilename;
    
    @Column(nullable = false, unique = true, length = 100)
    private String storedFilename;
    
    @Column(nullable = false, length = 1000)
    private String filePath;
    
    @Column(length = 1000)
    private String thumbnailPath;
    
    @Column(nullable = false)
    private Long fileSize;
    
    @Column(nullable = false, length = 100)
    private String contentType;
    
    @Column(nullable = false, length = 20)
    private String extension;
    
    private Integer width;
    
    private Integer height;
    
    @Column(length = 32, unique = true)
    private String md5;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    @Builder.Default
    private Long accessCount = 0L;
    
    @Column(nullable = false)
    @Builder.Default
    private Long downloadCount = 0L;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = true;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;
    
    @Column(length = 1000)
    private String description;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    private LocalDateTime lastAccessedAt;
    
    @Column(length = 50)
    private String ipAddress;
    
    public void incrementAccessCount() {
        this.accessCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }
    
    public void incrementDownloadCount() {
        this.downloadCount++;
    }
}
