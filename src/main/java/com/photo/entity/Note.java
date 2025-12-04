package com.photo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 便签实体类
 * 用于存储便签的基本信息，包括标题、内容和时间戳
 */
@Entity
@Table(name = "notes", indexes = {
    @Index(name = "idx_created_time", columnList = "created_time")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    
    /**
     * 便签唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 便签标题
     */
    @Column(nullable = false, length = 200)
    private String title;
    
    /**
     * 便签内容，支持Markdown格式
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    
    /**
     * 便签创建时间
     */
    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;
    
    /**
     * 便签最后修改时间
     */
    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
}
