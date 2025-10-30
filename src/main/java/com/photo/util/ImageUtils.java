package com.photo.util;

import com.photo.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片处理工具类
 */
@Slf4j
public class ImageUtils {
    
    /**
     * 获取图片尺寸
     */
    public static int[] getImageDimensions(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return new int[]{0, 0};
            }
            return new int[]{image.getWidth(), image.getHeight()};
        } catch (IOException e) {
            log.error("获取图片尺寸失败: {}", e.getMessage());
            return new int[]{0, 0};
        }
    }
    
    /**
     * 获取图片尺寸(从MultipartFile)
     */
    public static int[] getImageDimensions(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return new int[]{0, 0};
            }
            return new int[]{image.getWidth(), image.getHeight()};
        } catch (IOException e) {
            log.error("获取图片尺寸失败: {}", e.getMessage());
            return new int[]{0, 0};
        }
    }
    
    /**
     * 创建缩略图
     */
    public static void createThumbnail(File sourceFile, File thumbnailFile, int width, int height) {
        try {
            Thumbnails.of(sourceFile)
                    .size(width, height)
                    .keepAspectRatio(true)
                    .outputQuality(0.8)
                    .toFile(thumbnailFile);
            log.debug("创建缩略图成功: {}", thumbnailFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("创建缩略图失败: {}", e.getMessage());
            throw new FileStorageException("创建缩略图失败", e);
        }
    }
    
    /**
     * 压缩图片
     */
    public static void compressImage(File sourceFile, File targetFile, double quality, int maxWidth, int maxHeight) {
        try {
            BufferedImage originalImage = ImageIO.read(sourceFile);
            if (originalImage == null) {
                throw new FileStorageException("无效的图片文件");
            }
            
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            
            // 如果图片尺寸小于最大限制，只进行质量压缩
            if (width <= maxWidth && height <= maxHeight) {
                Thumbnails.of(sourceFile)
                        .scale(1.0)
                        .outputQuality(quality)
                        .toFile(targetFile);
            } else {
                // 同时进行尺寸和质量压缩
                Thumbnails.of(sourceFile)
                        .size(maxWidth, maxHeight)
                        .keepAspectRatio(true)
                        .outputQuality(quality)
                        .toFile(targetFile);
            }
            log.debug("压缩图片成功: {}", targetFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("压缩图片失败: {}", e.getMessage());
            throw new FileStorageException("压缩图片失败", e);
        }
    }
    
    /**
     * 压缩图片(覆盖原文件)
     */
    public static void compressImage(File file, double quality, int maxWidth, int maxHeight) {
        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        try {
            compressImage(file, tempFile, quality, maxWidth, maxHeight);
            // 删除原文件
            if (!file.delete()) {
                log.warn("删除原文件失败: {}", file.getAbsolutePath());
            }
            // 重命名临时文件
            if (!tempFile.renameTo(file)) {
                throw new FileStorageException("重命名临时文件失败");
            }
        } catch (Exception e) {
            // 清理临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }
            throw e;
        }
    }
    
    /**
     * 验证是否为有效图片
     */
    public static boolean isValidImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * 验证是否为有效图片(从MultipartFile)
     */
    public static boolean isValidImage(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * 旋转图片
     */
    public static void rotateImage(File sourceFile, File targetFile, double angle) {
        try {
            Thumbnails.of(sourceFile)
                    .scale(1.0)
                    .rotate(angle)
                    .toFile(targetFile);
            log.debug("旋转图片成功: {}", targetFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("旋转图片失败: {}", e.getMessage());
            throw new FileStorageException("旋转图片失败", e);
        }
    }
    
    /**
     * 添加水印
     */
    public static void addWatermark(File sourceFile, File watermarkFile, File targetFile, float opacity) {
        try {
            BufferedImage watermark = ImageIO.read(watermarkFile);
            Thumbnails.of(sourceFile)
                    .scale(1.0)
                    .watermark(net.coobird.thumbnailator.geometry.Positions.BOTTOM_RIGHT, watermark, opacity)
                    .toFile(targetFile);
            log.debug("添加水印成功: {}", targetFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("添加水印失败: {}", e.getMessage());
            throw new FileStorageException("添加水印失败", e);
        }
    }
}
