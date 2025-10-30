package com.photo.util;

import com.photo.exception.FileTypeException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件工具类
 */
public class FileUtils {
    
    private static final Tika tika = new Tika();
    
    /**
     * 允许的图片MIME类型
     */
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", 
        "image/gif", "image/bmp", "image/webp"
    );
    
    /**
     * 允许的图片扩展名
     */
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "webp"
    );
    
    /**
     * 生成唯一文件名
     */
    public static String generateUniqueFilename(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString().replace("-", "") + "." + extension;
    }
    
    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename).toLowerCase();
    }
    
    /**
     * 验证是否为图片文件
     */
    public static boolean isImageFile(MultipartFile file) {
        try {
            // 检查MIME类型
            String mimeType = detectMimeType(file);
            if (!ALLOWED_IMAGE_TYPES.contains(mimeType)) {
                return false;
            }
            
            // 检查文件扩展名
            String extension = getFileExtension(file.getOriginalFilename());
            return ALLOWED_IMAGE_EXTENSIONS.contains(extension);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检测文件MIME类型(使用Apache Tika)
     */
    public static String detectMimeType(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return tika.detect(inputStream);
        }
    }
    
    /**
     * 验证文件类型
     */
    public static void validateFileType(MultipartFile file, List<String> allowedTypes) {
        if (file == null || file.isEmpty()) {
            throw new FileTypeException("文件不能为空");
        }
        
        try {
            String mimeType = detectMimeType(file);
            if (!allowedTypes.contains(mimeType)) {
                throw new FileTypeException("不支持的文件类型: " + mimeType);
            }
            
            String extension = getFileExtension(file.getOriginalFilename());
            if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
                throw new FileTypeException("不支持的文件扩展名: " + extension);
            }
        } catch (IOException e) {
            throw new FileTypeException("文件类型检测失败", e);
        }
    }
    
    /**
     * 计算文件MD5值
     */
    public static String calculateMD5(MultipartFile file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(file.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("MD5计算失败", e);
        }
    }
    
    /**
     * 计算文件MD5值(从InputStream)
     */
    public static String calculateMD5(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[8192];
        int read;
        while ((read = inputStream.read(buffer)) > 0) {
            md.update(buffer, 0, read);
        }
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * 清理文件名(防止路径遍历攻击)
     */
    public static String sanitizeFilename(String filename) {
        if (filename == null) {
            return null;
        }
        // 移除路径分隔符和特殊字符
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
    
    /**
     * 验证文件名安全性
     */
    public static boolean isValidFilename(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        // 检查是否包含路径遍历字符
        return !filename.contains("..") && 
               !filename.contains("/") && 
               !filename.contains("\\");
    }
}
