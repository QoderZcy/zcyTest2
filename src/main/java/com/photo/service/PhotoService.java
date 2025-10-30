package com.photo.service;

import com.photo.config.FileStorageProperties;
import com.photo.dto.*;
import com.photo.entity.Photo;
import com.photo.exception.*;
import com.photo.repository.PhotoRepository;
import com.photo.util.FileUtils;
import com.photo.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 照片服务实现类
 */
@Slf4j
@Service
public class PhotoService {
    
    @Autowired
    private PhotoRepository photoRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private FileStorageProperties storageProperties;
    
    /**
     * 上传单个照片
     */
    @Transactional
    public PhotoUploadResponse uploadPhoto(MultipartFile file, String userId, String description) {
        log.info("开始上传照片: {}, 用户: {}", file.getOriginalFilename(), userId);
        
        // 验证文件
        validateFile(file);
        
        // 检查存储空间
        checkStorageSpace(file.getSize());
        
        // 计算MD5(用于去重)
        String md5 = FileUtils.calculateMD5(file);
        
        // 检查是否已存在相同文件
        Optional<Photo> existingPhoto = photoRepository.findByMd5(md5);
        if (existingPhoto.isPresent()) {
            log.info("文件已存在，返回已有记录: {}", md5);
            return convertToUploadResponse(existingPhoto.get());
        }
        
        try {
            // 存储文件
            String storedFilename = fileStorageService.storeFile(file);
            File storedFile = fileStorageService.getFile(storedFilename);
            
            // 获取图片尺寸
            int[] dimensions = ImageUtils.getImageDimensions(storedFile);
            
            // 创建缩略图
            String thumbnailFilename = fileStorageService.createThumbnail(storedFile, storedFilename);
            
            // 如果启用了压缩，压缩原图
            if (storageProperties.getCompression().getEnabled()) {
                fileStorageService.compressImage(storedFile);
            }
            
            // 保存照片信息到数据库
            Photo photo = Photo.builder()
                .originalFilename(file.getOriginalFilename())
                .storedFilename(storedFilename)
                .filePath(storedFile.getAbsolutePath())
                .thumbnailPath(thumbnailFilename)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .extension(FileUtils.getFileExtension(file.getOriginalFilename()))
                .width(dimensions[0])
                .height(dimensions[1])
                .md5(md5)
                .userId(userId)
                .description(description)
                .isPublic(true)
                .build();
            
            photo = photoRepository.save(photo);
            log.info("照片上传成功: ID={}, 文件名={}", photo.getId(), storedFilename);
            
            return convertToUploadResponse(photo);
        } catch (Exception e) {
            log.error("照片上传失败: {}", e.getMessage(), e);
            throw new FileStorageException("照片上传失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 批量上传照片
     */
    @Transactional
    public List<PhotoUploadResponse> uploadPhotos(MultipartFile[] files, String userId, String description) {
        log.info("开始批量上传照片: {} 个文件, 用户: {}", files.length, userId);
        
        if (files.length > storageProperties.getMaxFilesPerUpload()) {
            throw new FileSizeException("单次上传文件数不能超过 " + storageProperties.getMaxFilesPerUpload());
        }
        
        List<PhotoUploadResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                PhotoUploadResponse response = uploadPhoto(file, userId, description);
                responses.add(response);
            } catch (Exception e) {
                log.error("文件上传失败: {}", file.getOriginalFilename(), e);
                // 继续处理其他文件
            }
        }
        
        return responses;
    }
    
    /**
     * 获取照片信息
     */
    @Cacheable(value = "photos", key = "#id")
    public PhotoDTO getPhoto(Long id) {
        Photo photo = photoRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("照片不存在: " + id));
        
        if (photo.getDeleted()) {
            throw new FileNotFoundException("照片已被删除: " + id);
        }
        
        return convertToDTO(photo);
    }
    
    /**
     * 根据文件名获取照片
     */
    @Cacheable(value = "photos", key = "#filename")
    public Photo getPhotoByFilename(String filename) {
        return photoRepository.findByStoredFilename(filename)
            .orElseThrow(() -> new FileNotFoundException("照片不存在: " + filename));
    }
    
    /**
     * 获取用户的照片列表
     */
    public Page<PhotoDTO> getUserPhotos(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Photo> photos = photoRepository.findByUserIdAndDeletedFalse(userId, pageable);
        return photos.map(this::convertToDTO);
    }
    
    /**
     * 获取公开照片列表
     */
    public Page<PhotoDTO> getPublicPhotos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Photo> photos = photoRepository.findByIsPublicTrueAndDeletedFalse(pageable);
        return photos.map(this::convertToDTO);
    }
    
    /**
     * 搜索照片
     */
    public Page<PhotoDTO> searchPhotos(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Photo> photos = photoRepository.searchByFilename(keyword, pageable);
        return photos.map(this::convertToDTO);
    }
    
    /**
     * 删除照片(软删除)
     */
    @Transactional
    @CacheEvict(value = "photos", key = "#id")
    public void deletePhoto(Long id, String userId) {
        Photo photo = photoRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("照片不存在: " + id));
        
        // 验证权限
        if (!photo.getUserId().equals(userId)) {
            throw new AccessDeniedException("无权删除该照片");
        }
        
        photoRepository.softDeleteById(id);
        log.info("照片已软删除: ID={}", id);
    }
    
    /**
     * 物理删除照片
     */
    @Transactional
    @CacheEvict(value = "photos", key = "#id")
    public void permanentlyDeletePhoto(Long id, String userId) {
        Photo photo = photoRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("照片不存在: " + id));
        
        // 验证权限
        if (!photo.getUserId().equals(userId)) {
            throw new AccessDeniedException("无权删除该照片");
        }
        
        // 删除文件
        try {
            fileStorageService.deleteFile(photo.getStoredFilename());
            if (photo.getThumbnailPath() != null) {
                fileStorageService.deleteThumbnail(photo.getStoredFilename());
            }
        } catch (Exception e) {
            log.error("删除文件失败: {}", e.getMessage(), e);
        }
        
        // 删除数据库记录
        photoRepository.deleteById(id);
        log.info("照片已物理删除: ID={}", id);
    }
    
    /**
     * 增加访问次数
     */
    @Transactional
    public void incrementAccessCount(Long id) {
        photoRepository.incrementAccessCount(id, LocalDateTime.now());
    }
    
    /**
     * 增加下载次数
     */
    @Transactional
    public void incrementDownloadCount(Long id) {
        photoRepository.incrementDownloadCount(id);
    }
    
    /**
     * 获取存储信息
     */
    public StorageInfo getStorageInfo() {
        Long usedSpace = photoRepository.sumAllFileSize();
        Long totalSpace = storageProperties.getMaxStorageSize();
        Long freeSpace = totalSpace - usedSpace;
        Long totalFiles = photoRepository.countActiveFiles();
        
        return StorageInfo.builder()
            .usedSpace(usedSpace)
            .usedSpaceReadable(FileUtils.formatFileSize(usedSpace))
            .totalSpace(totalSpace)
            .totalSpaceReadable(FileUtils.formatFileSize(totalSpace))
            .freeSpace(freeSpace)
            .freeSpaceReadable(FileUtils.formatFileSize(freeSpace))
            .usagePercentage((double) usedSpace / totalSpace * 100)
            .totalFiles(totalFiles)
            .build();
    }
    
    /**
     * 定期清理过期文件
     */
    @Scheduled(cron = "${file.storage.cleanup.cron}")
    @Transactional
    public void cleanupExpiredFiles() {
        if (!storageProperties.getCleanup().getEnabled()) {
            return;
        }
        
        log.info("开始执行定期清理任务");
        LocalDateTime expiryDate = LocalDateTime.now()
            .minusDays(storageProperties.getCleanup().getDaysToKeep());
        
        List<Photo> expiredPhotos = photoRepository.findExpiredPhotos(expiryDate);
        log.info("找到 {} 个过期文件", expiredPhotos.size());
        
        for (Photo photo : expiredPhotos) {
            try {
                permanentlyDeletePhoto(photo.getId(), photo.getUserId());
            } catch (Exception e) {
                log.error("清理文件失败: {}", photo.getId(), e);
            }
        }
        
        log.info("定期清理任务完成");
    }
    
    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileTypeException("文件不能为空");
        }
        
        // 验证文件类型
        if (!FileUtils.isImageFile(file)) {
            throw new FileTypeException("只允许上传图片文件");
        }
        
        // 验证文件大小
        if (file.getSize() > storageProperties.getMaxFileSize()) {
            throw new FileSizeException("文件大小不能超过 " + 
                FileUtils.formatFileSize(storageProperties.getMaxFileSize()));
        }
        
        // 验证图片有效性
        if (!ImageUtils.isValidImage(file)) {
            throw new FileTypeException("无效的图片文件");
        }
    }
    
    /**
     * 检查存储空间
     */
    private void checkStorageSpace(long fileSize) {
        Long usedSpace = photoRepository.sumAllFileSize();
        Long maxSpace = storageProperties.getMaxStorageSize();
        
        if (usedSpace + fileSize > maxSpace) {
            throw new StorageFullException("存储空间不足");
        }
    }
    
    /**
     * 转换为上传响应DTO
     */
    private PhotoUploadResponse convertToUploadResponse(Photo photo) {
        return PhotoUploadResponse.builder()
            .id(photo.getId())
            .originalFilename(photo.getOriginalFilename())
            .storedFilename(photo.getStoredFilename())
            .fileSize(photo.getFileSize())
            .fileSizeReadable(FileUtils.formatFileSize(photo.getFileSize()))
            .contentType(photo.getContentType())
            .url("/api/photos/view/" + photo.getStoredFilename())
            .thumbnailUrl("/api/photos/thumbnail/" + photo.getStoredFilename())
            .downloadUrl("/api/photos/download/" + photo.getStoredFilename())
            .width(photo.getWidth())
            .height(photo.getHeight())
            .uploadedAt(photo.getCreatedAt())
            .md5(photo.getMd5())
            .build();
    }
    
    /**
     * 转换为PhotoDTO
     */
    private PhotoDTO convertToDTO(Photo photo) {
        return PhotoDTO.builder()
            .id(photo.getId())
            .originalFilename(photo.getOriginalFilename())
            .fileSize(photo.getFileSize())
            .fileSizeReadable(FileUtils.formatFileSize(photo.getFileSize()))
            .contentType(photo.getContentType())
            .url("/api/photos/view/" + photo.getStoredFilename())
            .thumbnailUrl("/api/photos/thumbnail/" + photo.getStoredFilename())
            .downloadUrl("/api/photos/download/" + photo.getStoredFilename())
            .width(photo.getWidth())
            .height(photo.getHeight())
            .accessCount(photo.getAccessCount())
            .downloadCount(photo.getDownloadCount())
            .isPublic(photo.getIsPublic())
            .description(photo.getDescription())
            .createdAt(photo.getCreatedAt())
            .updatedAt(photo.getUpdatedAt())
            .lastAccessedAt(photo.getLastAccessedAt())
            .build();
    }
}

