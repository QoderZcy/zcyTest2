package com.photo.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 安全工具类
 */
@Slf4j
public class SecurityUtils {
    
    /**
     * XSS过滤
     */
    public static String cleanXSS(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        return HtmlUtils.htmlEscape(value);
    }
    
    /**
     * 获取真实IP地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String[] headerNames = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        };
        
        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个IP值，第一个IP才是真实IP
                if (ip.contains(",")) {
                    ip = ip.split(",")[0];
                }
                return ip.trim();
            }
        }
        
        return request.getRemoteAddr();
    }
    
    /**
     * 验证Referer(防盗链)
     */
    public static boolean validateReferer(HttpServletRequest request, List<String> allowedDomains) {
        String referer = request.getHeader("Referer");
        
        // 如果没有Referer，允许直接访问(可根据需求调整)
        if (!StringUtils.hasText(referer)) {
            return true;
        }
        
        // 检查Referer是否在允许列表中
        for (String domain : allowedDomains) {
            if (referer.contains(domain)) {
                return true;
            }
        }
        
        log.warn("非法Referer访问: {}", referer);
        return false;
    }
    
    /**
     * 验证User-Agent
     */
    public static boolean isValidUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        
        // 如果没有User-Agent，可能是爬虫或恶意请求
        if (!StringUtils.hasText(userAgent)) {
            log.warn("请求缺少User-Agent");
            return false;
        }
        
        // 可以添加更多的验证规则
        return true;
    }
    
    /**
     * 生成安全的Token
     */
    public static String generateToken(String userId) {
        // 简单实现，实际项目中应使用JWT等更安全的方案
        return java.util.UUID.randomUUID().toString().replace("-", "") + "_" + userId;
    }
    
    /**
     * 验证Token
     */
    public static boolean validateToken(String token) {
        // 简单实现，实际项目中应使用JWT验证
        return StringUtils.hasText(token) && token.length() > 32;
    }
    
    /**
     * SQL注入防护(简单示例)
     */
    public static String cleanSQLInjection(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        
        // 移除危险字符
        String[] dangerousChars = {"'", "\"", ";", "--", "/*", "*/", "xp_", "sp_", "exec", "execute", "select", "insert", "update", "delete", "drop", "create", "alter"};
        String result = value;
        for (String ch : dangerousChars) {
            result = result.replaceAll("(?i)" + ch, "");
        }
        return result;
    }
    
    /**
     * 路径遍历攻击防护
     */
    public static boolean isPathTraversal(String path) {
        if (!StringUtils.hasText(path)) {
            return false;
        }
        
        // 检查是否包含路径遍历字符
        List<String> dangerousPatterns = Arrays.asList("..", "./", ".\\", "%2e%2e", "%252e%252e");
        for (String pattern : dangerousPatterns) {
            if (path.toLowerCase().contains(pattern)) {
                log.warn("检测到路径遍历攻击: {}", path);
                return true;
            }
        }
        return false;
    }
    
    /**
     * 验证文件访问权限
     */
    public static boolean checkFileAccess(String userId, String fileOwnerId, boolean isPublic) {
        // 公开文件可以访问
        if (isPublic) {
            return true;
        }
        
        // 文件所有者可以访问
        if (StringUtils.hasText(userId) && userId.equals(fileOwnerId)) {
            return true;
        }
        
        log.warn("用户{}尝试访问非公开文件，所有者为{}", userId, fileOwnerId);
        return false;
    }
}
