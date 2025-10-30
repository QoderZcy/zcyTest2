package com.photo.controller;

import com.photo.config.SecurityProperties;
import com.photo.dto.*;
import com.photo.entity.Photo;
import com.photo.exception.AccessDeniedException;
import com.photo.service.FileStorageService;
import com.photo.service.PhotoService;
import com.photo.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * 照片管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/photos")
@Tag(name = "照片管理", description = "照片上传、下载、查询等接口")
public class PhotoController {
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private SecurityProperties securityProperties;
    
    /**
     * 上传单个照片
     */
    @PostMapping("/upload")
    @Operation(summary = "上传单个照片", description = "支持图片格式：JPG、PNG、GIF等，最大10MB")
    public ResponseEntity<ApiResponse<PhotoUploadResponse>> uploadPhoto(
            @Parameter(description = "照片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "用户ID") @RequestParam(value = "userId", defaultValue = "guest") String userId,
            @Parameter(description = "照片描述") @RequestParam(value = "description", required = false) String description,
            HttpServletRequest request) {
        
        log.info("接收到上传请求: 文件={}, 用户={}, IP={}", 
            file.getOriginalFilename(), userId, SecurityUtils.getClientIpAddress(request));
        
        PhotoUploadResponse response = photoService.uploadPhoto(file, userId, description);
        return ResponseEntity.ok(ApiResponse.success("上传成功", response));
    }
    
    /**
     * 批量上传照片
     */
    @PostMapping("/upload/batch")
    @Operation(summary = "批量上传照片", description = "一次最多上传10个文件")
    public ResponseEntity<ApiResponse<List<PhotoUploadResponse>>> uploadPhotos(
            @Parameter(description = "照片文件数组") @RequestParam("files") MultipartFile[] files,
            @Parameter(description = "用户ID") @RequestParam(value = "userId", defaultValue = "guest") String userId,
            @Parameter(description = "照片描述") @RequestParam(value = "description", required = false) String description,
            HttpServletRequest request) {
        
        log.info("接收到批量上传请求: {} 个文件, 用户={}, IP={}", 
            files.length, userId, SecurityUtils.getClientIpAddress(request));
        
        List<PhotoUploadResponse> responses = photoService.uploadPhotos(files, userId, description);
        return ResponseEntity.ok(ApiResponse.success("批量上传成功", responses));
    }
    
    /**
     * 在线预览照片
     */
    @GetMapping("/view/{filename:.+}")
    @Operation(summary = "在线预览照片", description = "通过文件名预览照片")
    public ResponseEntity<byte[]> viewPhoto(
            @Parameter(description = "文件名") @PathVariable String filename,
            HttpServletRequest request) {
        
        log.debug("预览照片: {}, IP={}", filename, SecurityUtils.getClientIpAddress(request));
        
        // 防盗链检查
        if (securityProperties.getReferer().getEnabled()) {
            if (!SecurityUtils.validateReferer(request, securityProperties.getReferer().getAllowedDomains())) {
                throw new AccessDeniedException("非法访问来源");
            }
        }
        
        Photo photo = photoService.getPhotoByFilename(filename);
        photoService.incrementAccessCount(photo.getId());
        
        try {
            File file = fileStorageService.getFile(filename);
            byte[] content = Files.readAllBytes(file.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(photo.getContentType()));
            headers.setCacheControl(CacheControl.maxAge(3600, java.util.concurrent.TimeUnit.SECONDS));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(content);
        } catch (IOException e) {
            log.error("读取文件失败: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 查看缩略图
     */
    @GetMapping("/thumbnail/{filename:.+}")
    @Operation(summary = "查看缩略图", description = "获取照片缩略图")
    public ResponseEntity<byte[]> viewThumbnail(
            @Parameter(description = "文件名") @PathVariable String filename) {
        
        log.debug("查看缩略图: {}", filename);
        
        try {
            File thumbnail = fileStorageService.getThumbnail(filename);
            byte[] content = Files.readAllBytes(thumbnail.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setCacheControl(CacheControl.maxAge(7200, java.util.concurrent.TimeUnit.SECONDS));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(content);
        } catch (IOException e) {
            log.error("读取缩略图失败: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 下载照片
     */
    @GetMapping("/download/{filename:.+}")
    @Operation(summary = "下载照片", description = "下载原图文件")
    public ResponseEntity<byte[]> downloadPhoto(
            @Parameter(description = "文件名") @PathVariable String filename,
            HttpServletRequest request) {
        
        log.info("下载照片: {}, IP={}", filename, SecurityUtils.getClientIpAddress(request));
        
        Photo photo = photoService.getPhotoByFilename(filename);
        photoService.incrementDownloadCount(photo.getId());
        
        try {
            File file = fileStorageService.getFile(filename);
            byte[] content = Files.readAllBytes(file.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(
                ContentDisposition.attachment()
                    .filename(photo.getOriginalFilename(), java.nio.charset.StandardCharsets.UTF_8)
                    .build()
            );
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(content);
        } catch (IOException e) {
            log.error("下载文件失败: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 断点续传下载
     */
    @GetMapping("/download/range/{filename:.+}")
    @Operation(summary = "断点续传下载", description = "支持Range请求的文件下载")
    public ResponseEntity<byte[]> downloadPhotoWithRange(
            @Parameter(description = "文件名") @PathVariable String filename,
            @RequestHeader(value = "Range", required = false) String range,
            HttpServletRequest request) {
        
        log.info("断点续传下载: {}, Range={}, IP={}", filename, range, SecurityUtils.getClientIpAddress(request));
        
        Photo photo = photoService.getPhotoByFilename(filename);
        long fileSize = fileStorageService.getFileSize(filename);
        
        // 解析Range头
        long start = 0;
        long end = fileSize - 1;
        
        if (range != null && range.startsWith("bytes=")) {
            String[] ranges = range.substring(6).split("-");
            try {
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1 && !ranges[1].isEmpty()) {
                    end = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException e) {
                log.error("解析Range头失败: {}", range);
                return ResponseEntity.badRequest().build();
            }
        }
        
        byte[] content = fileStorageService.readFileRange(filename, start, end);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getContentType()));
        headers.setContentLength(content.length);
        headers.set("Accept-Ranges", "bytes");
        headers.set("Content-Range", String.format("bytes %d-%d/%d", start, end, fileSize));
        
        return ResponseEntity
            .status(range != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
            .headers(headers)
            .body(content);
    }
    
    /**
     * 获取照片信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取照片信息", description = "根据ID获取照片详细信息")
    public ResponseEntity<ApiResponse<PhotoDTO>> getPhoto(
            @Parameter(description = "照片ID") @PathVariable Long id) {
        
        PhotoDTO photo = photoService.getPhoto(id);
        return ResponseEntity.ok(ApiResponse.success(photo));
    }
    
    /**
     * 获取用户的照片列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户照片列表", description = "分页查询用户上传的照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> getUserPhotos(
            @Parameter(description = "用户ID") @PathVariable String userId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.getUserPhotos(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 获取公开照片列表
     */
    @GetMapping("/public")
    @Operation(summary = "获取公开照片列表", description = "分页查询所有公开照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> getPublicPhotos(
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.getPublicPhotos(page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 搜索照片
     */
    @GetMapping("/search")
    @Operation(summary = "搜索照片", description = "根据文件名关键词搜索照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> searchPhotos(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.searchPhotos(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 删除照片
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除照片", description = "软删除照片，不会立即删除文件")
    public ResponseEntity<ApiResponse<Void>> deletePhoto(
            @Parameter(description = "照片ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam String userId) {
        
        photoService.deletePhoto(id, userId);
        return ResponseEntity.ok(ApiResponse.success("删除成功", null));
    }
    
    /**
     * 永久删除照片
     */
    @DeleteMapping("/{id}/permanent")
    @Operation(summary = "永久删除照片", description = "物理删除照片及文件")
    public ResponseEntity<ApiResponse<Void>> permanentlyDeletePhoto(
            @Parameter(description = "照片ID") @PathVariable Long id,
            @Parameter(description = "用户ID") @RequestParam String userId) {
        
        photoService.permanentlyDeletePhoto(id, userId);
        return ResponseEntity.ok(ApiResponse.success("永久删除成功", null));
    }
    
    /**
     * 获取存储空间信息
     */
    @GetMapping("/storage/info")
    @Operation(summary = "获取存储空间信息", description = "查询存储空间使用情况")
    public ResponseEntity<ApiResponse<StorageInfo>> getStorageInfo() {
        StorageInfo info = photoService.getStorageInfo();
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
