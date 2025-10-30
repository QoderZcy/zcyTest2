package com.photo.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileUtils单元测试
 */
class FileUtilsTest {
    
    @Test
    void testGenerateUniqueFilename() {
        // When
        String filename1 = FileUtils.generateUniqueFilename("test.jpg");
        String filename2 = FileUtils.generateUniqueFilename("test.jpg");
        
        // Then
        assertNotNull(filename1);
        assertNotNull(filename2);
        assertNotEquals(filename1, filename2); // 每次生成的文件名应该不同
        assertTrue(filename1.endsWith(".jpg"));
    }
    
    @Test
    void testGetFileExtension() {
        // When
        String ext1 = FileUtils.getFileExtension("test.jpg");
        String ext2 = FileUtils.getFileExtension("test.PNG");
        String ext3 = FileUtils.getFileExtension("test");
        
        // Then
        assertEquals("jpg", ext1);
        assertEquals("png", ext2);
        assertEquals("", ext3);
    }
    
    @Test
    void testFormatFileSize() {
        // When
        String size1 = FileUtils.formatFileSize(500);
        String size2 = FileUtils.formatFileSize(1024);
        String size3 = FileUtils.formatFileSize(1024 * 1024);
        String size4 = FileUtils.formatFileSize(1024L * 1024 * 1024);
        
        // Then
        assertEquals("500 B", size1);
        assertEquals("1.00 KB", size2);
        assertEquals("1.00 MB", size3);
        assertEquals("1.00 GB", size4);
    }
    
    @Test
    void testSanitizeFilename() {
        // When
        String safe1 = FileUtils.sanitizeFilename("test file.jpg");
        String safe2 = FileUtils.sanitizeFilename("../../../etc/passwd");
        String safe3 = FileUtils.sanitizeFilename("test<script>.jpg");
        
        // Then
        assertNotNull(safe1);
        assertFalse(safe2.contains(".."));
        assertFalse(safe3.contains("<"));
        assertFalse(safe3.contains(">"));
    }
    
    @Test
    void testIsValidFilename() {
        // When & Then
        assertTrue(FileUtils.isValidFilename("test.jpg"));
        assertTrue(FileUtils.isValidFilename("my-photo_2024.png"));
        assertFalse(FileUtils.isValidFilename("../test.jpg"));
        assertFalse(FileUtils.isValidFilename("test/file.jpg"));
        assertFalse(FileUtils.isValidFilename("test\\file.jpg"));
        assertFalse(FileUtils.isValidFilename(""));
        assertFalse(FileUtils.isValidFilename(null));
    }
    
    @Test
    void testCalculateMD5() {
        // Given
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "test content".getBytes()
        );
        
        // When
        String md5 = FileUtils.calculateMD5(file);
        
        // Then
        assertNotNull(md5);
        assertEquals(32, md5.length()); // MD5是32位16进制字符串
    }
}
