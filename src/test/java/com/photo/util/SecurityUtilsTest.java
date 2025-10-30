package com.photo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SecurityUtils单元测试
 */
class SecurityUtilsTest {
    
    @Test
    void testCleanXSS() {
        // When
        String clean1 = SecurityUtils.cleanXSS("<script>alert('XSS')</script>");
        String clean2 = SecurityUtils.cleanXSS("Normal text");
        String clean3 = SecurityUtils.cleanXSS("<img src=x onerror=alert(1)>");
        
        // Then
        assertFalse(clean1.contains("<script>"));
        assertEquals("Normal text", clean2);
        assertFalse(clean3.contains("onerror"));
    }
    
    @Test
    void testGetClientIpAddress() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("192.168.1.1");
        
        // When
        String ip = SecurityUtils.getClientIpAddress(request);
        
        // Then
        assertEquals("192.168.1.1", ip);
    }
    
    @Test
    void testGetClientIpAddress_WithXForwardedFor() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Forwarded-For", "203.0.113.1, 198.51.100.1");
        request.setRemoteAddr("192.168.1.1");
        
        // When
        String ip = SecurityUtils.getClientIpAddress(request);
        
        // Then
        assertEquals("203.0.113.1", ip); // 应该返回第一个IP
    }
    
    @Test
    void testValidateReferer_ValidDomain() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Referer", "http://localhost:8080/page");
        List<String> allowedDomains = Arrays.asList("localhost", "example.com");
        
        // When
        boolean isValid = SecurityUtils.validateReferer(request, allowedDomains);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    void testValidateReferer_InvalidDomain() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Referer", "http://malicious.com/page");
        List<String> allowedDomains = Arrays.asList("localhost", "example.com");
        
        // When
        boolean isValid = SecurityUtils.validateReferer(request, allowedDomains);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    void testValidateReferer_NoReferer() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<String> allowedDomains = Arrays.asList("localhost", "example.com");
        
        // When
        boolean isValid = SecurityUtils.validateReferer(request, allowedDomains);
        
        // Then
        assertTrue(isValid); // 没有Referer时允许访问
    }
    
    @Test
    void testIsPathTraversal() {
        // When & Then
        assertTrue(SecurityUtils.isPathTraversal("../../../etc/passwd"));
        assertTrue(SecurityUtils.isPathTraversal("./config"));
        assertTrue(SecurityUtils.isPathTraversal("..\\windows\\system32"));
        assertFalse(SecurityUtils.isPathTraversal("normal/path/file.txt"));
        assertFalse(SecurityUtils.isPathTraversal(""));
        assertFalse(SecurityUtils.isPathTraversal(null));
    }
    
    @Test
    void testCheckFileAccess_PublicFile() {
        // When
        boolean canAccess = SecurityUtils.checkFileAccess("user1", "user2", true);
        
        // Then
        assertTrue(canAccess); // 公开文件任何人都可以访问
    }
    
    @Test
    void testCheckFileAccess_Owner() {
        // When
        boolean canAccess = SecurityUtils.checkFileAccess("user1", "user1", false);
        
        // Then
        assertTrue(canAccess); // 所有者可以访问自己的私有文件
    }
    
    @Test
    void testCheckFileAccess_NotOwner() {
        // When
        boolean canAccess = SecurityUtils.checkFileAccess("user1", "user2", false);
        
        // Then
        assertFalse(canAccess); // 非所有者不能访问私有文件
    }
    
    @Test
    void testGenerateToken() {
        // When
        String token1 = SecurityUtils.generateToken("user1");
        String token2 = SecurityUtils.generateToken("user1");
        
        // Then
        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2); // 每次生成的token应该不同
        assertTrue(token1.contains("user1"));
    }
    
    @Test
    void testValidateToken() {
        // When & Then
        assertTrue(SecurityUtils.validateToken("a".repeat(33)));
        assertFalse(SecurityUtils.validateToken("short"));
        assertFalse(SecurityUtils.validateToken(""));
        assertFalse(SecurityUtils.validateToken(null));
    }
}
