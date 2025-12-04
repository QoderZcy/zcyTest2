package com.photo.controller;

import com.photo.config.SecurityProperties;
import com.photo.dto.*;
import com.photo.entity.Photo;
import com.photo.exception.AccessDeniedException;
import com.photo.service.FileStorageService;
import com.photo.service.PhotoService;
import com.photo.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Api(tags = "照片管理")
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
    @ApiOperation(value = "上传单个照片", notes = "支持图片格式：JPG、PNG、GIF等，最大9MB")
    public ResponseEntity<ApiResponse<PhotoUploadResponse>> uploadPhoto(
            @ApiParam("照片文件") @RequestParam("file") MultipartFile file,
            @ApiParam("用户ID") @RequestParam(value = "userId", defaultValue = "guest") String userId,
            @ApiParam("照片描述") @RequestParam(value = "description", required = false) String description,
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
    @ApiOperation(value = "批量上传照片", notes = "一次最多上传10个文件")
    public ResponseEntity<ApiResponse<List<PhotoUploadResponse>>> uploadPhotos(
            @ApiParam("照片文件数组") @RequestParam("files") MultipartFile[] files,
            @ApiParam("用户ID") @RequestParam(value = "userId", defaultValue = "guest") String userId,
            @ApiParam("照片描述") @RequestParam(value = "description", required = false) String description,
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
    @ApiOperation(value = "在线预览照片", notes = "通过文件名预览照片")
    public ResponseEntity<byte[]> viewPhoto(
            @ApiParam("文件名") @PathVariable String filename,
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
    @ApiOperation(value = "查看缩略图", notes = "获取照片缩略图")
    public ResponseEntity<byte[]> viewThumbnail(
            @ApiParam("文件名") @PathVariable String filename) {
        
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
    @ApiOperation(value = "下载照片", notes = "下载原图文件")
    public ResponseEntity<byte[]> downloadPhoto(
            @ApiParam("文件名") @PathVariable String filename,
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
    @ApiOperation(value = "断点续传下载", notes = "支持Range请求的文件下载")
    public ResponseEntity<byte[]> downloadPhotoWithRange(
            @ApiParam("文件名") @PathVariable String filename,
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
    @ApiOperation(value = "获取照片信息", notes = "根据ID获取照片详细信息")
    public ResponseEntity<ApiResponse<PhotoDTO>> getPhoto(
            @ApiParam("照片ID") @PathVariable Long id) {
        
        PhotoDTO photo = photoService.getPhoto(id);
        return ResponseEntity.ok(ApiResponse.success(photo));
    }
    
    /**
     * 获取用户的照片列表
     */
    @GetMapping("/user/{userId}")
    @ApiOperation(value = "获取用户照片列表", notes = "分页查询用户上传的照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> getUserPhotos(
            @ApiParam("用户ID") @PathVariable String userId,
            @ApiParam("页码") @RequestParam(defaultValue = "0") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.getUserPhotos(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 获取公开照片列表
     */
    @GetMapping("/public")
    @ApiOperation(value = "获取公开照片列表", notes = "分页查询所有公开照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> getPublicPhotos(
            @ApiParam("页码") @RequestParam(defaultValue = "0") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.getPublicPhotos(page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 搜索照片
     */
    @GetMapping("/search")
    @ApiOperation(value = "搜索照片", notes = "根据文件名关键词搜索照片")
    public ResponseEntity<ApiResponse<Page<PhotoDTO>>> searchPhotos(
            @ApiParam("搜索关键词") @RequestParam String keyword,
            @ApiParam("页码") @RequestParam(defaultValue = "0") int page,
            @ApiParam("每页数量") @RequestParam(defaultValue = "20") int size) {
        
        Page<PhotoDTO> photos = photoService.searchPhotos(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }
    
    /**
     * 删除照片
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除照片", notes = "软删除照片，不会立即删除文件")
    public ResponseEntity<ApiResponse<Void>> deletePhoto(
            @ApiParam("照片ID") @PathVariable Long id,
            @ApiParam("用户ID") @RequestParam String userId) {
        
        photoService.deletePhoto(id, userId);
        return ResponseEntity.ok(ApiResponse.success("删除成功", null));
    }
    
    /**
     * 永久删除照片
     */
    @DeleteMapping("/{id}/permanent")
    @ApiOperation(value = "永久删除照片", notes = "物理删除照片及文件")
    public ResponseEntity<ApiResponse<Void>> permanentlyDeletePhoto(
            @ApiParam("照片ID") @PathVariable Long id,
            @ApiParam("用户ID") @RequestParam String userId) {
        
        photoService.permanentlyDeletePhoto(id, userId);
        return ResponseEntity.ok(ApiResponse.success("永久删除成功", null));
    }
    
    /**
     * 获取存储空间信息
     */
    @GetMapping("/storage/info")
    @ApiOperation(value = "获取存储空间信息", notes = "查询存储空间使用情况")
    public ResponseEntity<ApiResponse<StorageInfo>> getStorageInfo() {
        StorageInfo info = photoService.getStorageInfo();
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
