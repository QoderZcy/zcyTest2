package com.photo.service;

import com.photo.config.FileStorageProperties;
import com.photo.exception.FileStorageException;
import com.photo.util.FileUtils;
import com.photo.util.ImageUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件存储服务
 */
@Slf4j
@Service
public class FileStorageService {
    
    @Autowired
    private FileStorageProperties storageProperties;
    
    private Path baseLocation;
    private Path tempLocation;
    private Path thumbnailLocation;
    
    /**
     * 初始化存储目录
     */
    @PostConstruct
    public void init() {
        try {
            baseLocation = Paths.get(storageProperties.getBasePath()).toAbsolutePath().normalize();
            tempLocation = Paths.get(storageProperties.getTempPath()).toAbsolutePath().normalize();
            thumbnailLocation = Paths.get(storageProperties.getThumbnailPath()).toAbsolutePath().normalize();
            
            Files.createDirectories(baseLocation);
            Files.createDirectories(tempLocation);
            Files.createDirectories(thumbnailLocation);
            
            log.info("文件存储目录初始化成功: {}", baseLocation);
            log.info("临时文件目录: {}", tempLocation);
            log.info("缩略图目录: {}", thumbnailLocation);
        } catch (IOException e) {
            log.error("无法创建存储目录", e);
            throw new FileStorageException("无法创建存储目录", e);
        }
    }
    
    /**
     * 存储文件
     */
    public String storeFile(MultipartFile file) {
        // 验证文件名
        String originalFilename = file.getOriginalFilename();
        if (!FileUtils.isValidFilename(originalFilename)) {
            throw new FileStorageException("文件名包含非法字符: " + originalFilename);
        }
        
        // 生成唯一文件名
        String storedFilename = FileUtils.generateUniqueFilename(originalFilename);
        
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                throw new FileStorageException("无法存储空文件: " + originalFilename);
            }
            
            // 解析存储路径
            Path destinationFile = baseLocation.resolve(Paths.get(storedFilename))
                .normalize().toAbsolutePath();
            
            // 验证目标路径
            if (!destinationFile.getParent().equals(baseLocation)) {
                throw new FileStorageException("无法在目标目录外存储文件");
            }
            
            // 复制文件
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            
            log.info("文件存储成功: {}", storedFilename);
            return storedFilename;
        } catch (IOException e) {
            log.error("文件存储失败: {}", e.getMessage(), e);
            throw new FileStorageException("文件存储失败: " + originalFilename, e);
        }
    }
    
    /**
     * 获取文件
     */
    public File getFile(String filename) {
        try {
            Path filePath = baseLocation.resolve(filename).normalize();
            File file = filePath.toFile();
            
            if (!file.exists() || !file.isFile()) {
                throw new FileStorageException("文件不存在: " + filename);
            }
            
            return file;
        } catch (Exception e) {
            log.error("获取文件失败: {}", e.getMessage());
            throw new FileStorageException("获取文件失败: " + filename, e);
        }
    }
    
    /**
     * 获取文件路径
     */
    public Path getFilePath(String filename) {
        return baseLocation.resolve(filename).normalize();
    }
    
    /**
     * 获取缩略图
     */
    public File getThumbnail(String filename) {
        try {
            String thumbnailFilename = "thumb_" + filename;
            Path thumbnailPath = thumbnailLocation.resolve(thumbnailFilename).normalize();
            File thumbnailFile = thumbnailPath.toFile();
            
            if (!thumbnailFile.exists() || !thumbnailFile.isFile()) {
                throw new FileStorageException("缩略图不存在: " + filename);
            }
            
            return thumbnailFile;
        } catch (Exception e) {
            log.error("获取缩略图失败: {}", e.getMessage());
            throw new FileStorageException("获取缩略图失败: " + filename, e);
        }
    }
    
    /**
     * 创建缩略图
     */
    public String createThumbnail(File sourceFile, String filename) {
        try {
            String thumbnailFilename = "thumb_" + filename;
            Path thumbnailPath = thumbnailLocation.resolve(thumbnailFilename).normalize();
            File thumbnailFile = thumbnailPath.toFile();
            
            ImageUtils.createThumbnail(
                sourceFile, 
                thumbnailFile,
                storageProperties.getThumbnail().getWidth(),
                storageProperties.getThumbnail().getHeight()
            );
            
            log.debug("缩略图创建成功: {}", thumbnailFilename);
            return thumbnailPath.toString();
        } catch (Exception e) {
            log.error("创建缩略图失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 压缩图片
     */
    public void compressImage(File file) {
        try {
            ImageUtils.compressImage(
                file,
                storageProperties.getCompression().getQuality(),
                storageProperties.getCompression().getMaxWidth(),
                storageProperties.getCompression().getMaxHeight()
            );
            log.debug("图片压缩成功: {}", file.getName());
        } catch (Exception e) {
            log.error("图片压缩失败: {}", e.getMessage());
            // 压缩失败不影响整体流程
        }
    }
    
    /**
     * 删除文件
     */
    public void deleteFile(String filename) {
        try {
            Path filePath = baseLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("文件删除成功: {}", filename);
        } catch (IOException e) {
            log.error("文件删除失败: {}", e.getMessage());
            throw new FileStorageException("文件删除失败: " + filename, e);
        }
    }
    
    /**
     * 删除缩略图
     */
    public void deleteThumbnail(String filename) {
        try {
            String thumbnailFilename = "thumb_" + filename;
            Path thumbnailPath = thumbnailLocation.resolve(thumbnailFilename).normalize();
            Files.deleteIfExists(thumbnailPath);
            log.info("缩略图删除成功: {}", thumbnailFilename);
        } catch (IOException e) {
            log.error("缩略图删除失败: {}", e.getMessage());
        }
    }
    
    /**
     * 读取文件内容
     */
    public byte[] readFileContent(String filename) {
        try {
            Path filePath = baseLocation.resolve(filename).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("读取文件失败: {}", e.getMessage());
            throw new FileStorageException("读取文件失败: " + filename, e);
        }
    }
    
    /**
     * 读取文件部分内容(支持断点续传)
     */
    public byte[] readFileRange(String filename, long start, long end) {
        try {
            Path filePath = baseLocation.resolve(filename).normalize();
            File file = filePath.toFile();
            
            long fileSize = file.length();
            if (start >= fileSize) {
                throw new FileStorageException("起始位置超出文件大小");
            }
            
            // 调整结束位置
            if (end >= fileSize) {
                end = fileSize - 1;
            }
            
            int length = (int) (end - start + 1);
            byte[] buffer = new byte[length];
            
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                raf.seek(start);
                raf.readFully(buffer);
            }
            
            return buffer;
        } catch (IOException e) {
            log.error("读取文件范围失败: {}", e.getMessage());
            throw new FileStorageException("读取文件失败: " + filename, e);
        }
    }
    
    /**
     * 获取文件大小
     */
    public long getFileSize(String filename) {
        try {
            Path filePath = baseLocation.resolve(filename).normalize();
            return Files.size(filePath);
        } catch (IOException e) {
            log.error("获取文件大小失败: {}", e.getMessage());
            throw new FileStorageException("获取文件大小失败: " + filename, e);
        }
    }
    
    /**
     * 检查文件是否存在
     */
    public boolean fileExists(String filename) {
        Path filePath = baseLocation.resolve(filename).normalize();
        return Files.exists(filePath);
    }
    
    /**
     * 清理所有文件
     */
    public void deleteAll() {
        try {
            Files.walk(baseLocation)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        log.error("删除文件失败: {}", path, e);
                    }
                });
            log.info("所有文件已清理");
        } catch (IOException e) {
            log.error("清理文件失败", e);
        }
    }
}
