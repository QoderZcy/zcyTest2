package com.photo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs", indexes = {
    @Index(name = "idx_blog_created_time", columnList = "created_time"),
    @Index(name = "idx_blog_author", columnList = "author")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 300)
    private String title;
    
    @Column(nullable = false, length = 100)
    private String author;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(length = 50)
    private String category;
    
    @Column(length = 200)
    private String tags;
    
    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;
    
    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
}
