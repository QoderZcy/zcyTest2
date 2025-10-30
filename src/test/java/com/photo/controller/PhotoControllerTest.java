package com.photo.controller;

import com.photo.config.SecurityProperties;
import com.photo.dto.ApiResponse;
import com.photo.dto.PhotoDTO;
import com.photo.dto.PhotoUploadResponse;
import com.photo.entity.Photo;
import com.photo.service.FileStorageService;
import com.photo.service.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PhotoController单元测试
 */
@WebMvcTest(PhotoController.class)
class PhotoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PhotoService photoService;
    
    @MockBean
    private FileStorageService fileStorageService;
    
    @MockBean
    private SecurityProperties securityProperties;
    
    private PhotoUploadResponse uploadResponse;
    private PhotoDTO photoDTO;
    
    @BeforeEach
    void setUp() {
        uploadResponse = PhotoUploadResponse.builder()
            .id(1L)
            .originalFilename("test.jpg")
            .storedFilename("abc123.jpg")
            .fileSize(1024L)
            .fileSizeReadable("1.00 KB")
            .contentType("image/jpeg")
            .url("/api/photos/view/abc123.jpg")
            .thumbnailUrl("/api/photos/thumbnail/abc123.jpg")
            .downloadUrl("/api/photos/download/abc123.jpg")
            .uploadedAt(LocalDateTime.now())
            .build();
        
        photoDTO = PhotoDTO.builder()
            .id(1L)
            .originalFilename("test.jpg")
            .fileSize(1024L)
            .fileSizeReadable("1.00 KB")
            .contentType("image/jpeg")
            .url("/api/photos/view/abc123.jpg")
            .thumbnailUrl("/api/photos/thumbnail/abc123.jpg")
            .downloadUrl("/api/photos/download/abc123.jpg")
            .accessCount(0L)
            .downloadCount(0L)
            .isPublic(true)
            .createdAt(LocalDateTime.now())
            .build();
    }
    
    @Test
    void testUploadPhoto_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.jpg",
            "image/jpeg",
            "test image content".getBytes()
        );
        
        when(photoService.uploadPhoto(any(), anyString(), anyString()))
            .thenReturn(uploadResponse);
        
        // When & Then
        mockMvc.perform(multipart("/photos/upload")
                .file(file)
                .param("userId", "testUser")
                .param("description", "Test photo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.originalFilename").value("test.jpg"));
        
        verify(photoService, times(1)).uploadPhoto(any(), eq("testUser"), eq("Test photo"));
    }
    
    @Test
    void testGetPhoto_Success() throws Exception {
        // Given
        when(photoService.getPhoto(1L)).thenReturn(photoDTO);
        
        // When & Then
        mockMvc.perform(get("/photos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.originalFilename").value("test.jpg"));
        
        verify(photoService, times(1)).getPhoto(1L);
    }
    
    @Test
    void testGetPublicPhotos_Success() throws Exception {
        // Given
        List<PhotoDTO> photoList = Arrays.asList(photoDTO);
        Page<PhotoDTO> photoPage = new PageImpl<>(photoList);
        when(photoService.getPublicPhotos(0, 20)).thenReturn(photoPage);
        
        // When & Then
        mockMvc.perform(get("/photos/public")
                .param("page", "0")
                .param("size", "20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.content").isArray());
        
        verify(photoService, times(1)).getPublicPhotos(0, 20);
    }
    
    @Test
    void testSearchPhotos_Success() throws Exception {
        // Given
        List<PhotoDTO> photoList = Arrays.asList(photoDTO);
        Page<PhotoDTO> photoPage = new PageImpl<>(photoList);
        when(photoService.searchPhotos("test", 0, 20)).thenReturn(photoPage);
        
        // When & Then
        mockMvc.perform(get("/photos/search")
                .param("keyword", "test")
                .param("page", "0")
                .param("size", "20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
        
        verify(photoService, times(1)).searchPhotos("test", 0, 20);
    }
    
    @Test
    void testDeletePhoto_Success() throws Exception {
        // Given
        doNothing().when(photoService).deletePhoto(1L, "testUser");
        
        // When & Then
        mockMvc.perform(delete("/photos/1")
                .param("userId", "testUser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("删除成功"));
        
        verify(photoService, times(1)).deletePhoto(1L, "testUser");
    }
}
