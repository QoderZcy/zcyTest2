package com.photo.repository;

import com.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 照片数据访问层
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    
    /**
     * 根据存储文件名查找照片
     */
    Optional<Photo> findByStoredFilename(String storedFilename);
    
    /**
     * 根据MD5查找照片(用于去重)
     */
    Optional<Photo> findByMd5(String md5);
    
    /**
     * 根据用户ID查找照片
     */
    Page<Photo> findByUserIdAndDeletedFalse(String userId, Pageable pageable);
    
    /**
     * 查找公开照片
     */
    Page<Photo> findByIsPublicTrueAndDeletedFalse(Pageable pageable);
    
    /**
     * 根据原始文件名模糊查询
     */
    @Query("SELECT p FROM Photo p WHERE p.originalFilename LIKE %:filename% AND p.deleted = false")
    Page<Photo> searchByFilename(@Param("filename") String filename, Pageable pageable);
    
    /**
     * 统计用户上传的文件总大小
     */
    @Query("SELECT COALESCE(SUM(p.fileSize), 0) FROM Photo p WHERE p.userId = :userId AND p.deleted = false")
    Long sumFileSizeByUserId(@Param("userId") String userId);
    
    /**
     * 统计所有文件总大小
     */
    @Query("SELECT COALESCE(SUM(p.fileSize), 0) FROM Photo p WHERE p.deleted = false")
    Long sumAllFileSize();
    
    /**
     * 统计未删除的文件数量
     */
    @Query("SELECT COUNT(p) FROM Photo p WHERE p.deleted = false")
    Long countActiveFiles();
    
    /**
     * 查找过期文件(用于定期清理)
     */
    @Query("SELECT p FROM Photo p WHERE p.createdAt < :expiryDate AND p.deleted = false")
    List<Photo> findExpiredPhotos(@Param("expiryDate") LocalDateTime expiryDate);
    
    /**
     * 软删除照片
     */
    @Modifying
    @Query("UPDATE Photo p SET p.deleted = true WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);
    
    /**
     * 批量软删除照片
     */
    @Modifying
    @Query("UPDATE Photo p SET p.deleted = true WHERE p.id IN :ids")
    void softDeleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 增加访问次数
     */
    @Modifying
    @Query("UPDATE Photo p SET p.accessCount = p.accessCount + 1, p.lastAccessedAt = :accessTime WHERE p.id = :id")
    void incrementAccessCount(@Param("id") Long id, @Param("accessTime") LocalDateTime accessTime);
    
    /**
     * 增加下载次数
     */
    @Modifying
    @Query("UPDATE Photo p SET p.downloadCount = p.downloadCount + 1 WHERE p.id = :id")
    void incrementDownloadCount(@Param("id") Long id);
    
    /**
     * 查找热门照片(按访问次数排序)
     */
    @Query("SELECT p FROM Photo p WHERE p.isPublic = true AND p.deleted = false ORDER BY p.accessCount DESC")
    Page<Photo> findPopularPhotos(Pageable pageable);
    
    /**
     * 查找最新照片
     */
    @Query("SELECT p FROM Photo p WHERE p.isPublic = true AND p.deleted = false ORDER BY p.createdAt DESC")
    Page<Photo> findLatestPhotos(Pageable pageable);
}
