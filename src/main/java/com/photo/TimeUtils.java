package com.photo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类 - 用于打印当前时间
 */
public class TimeUtils {
    
    /**
     * 打印当前时间 (默认格式)
     */
    public static void printCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);
        System.out.println("当前时间: " + formattedTime);
    }
    
    /**
     * 打印当前时间 (自定义格式)
     * @param pattern 时间格式,例如: "yyyy-MM-dd HH:mm:ss"
     */
    public static void printCurrentTime(String pattern) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedTime = now.format(formatter);
        System.out.println("当前时间: " + formattedTime);
    }
    
    /**
     * 获取当前时间字符串
     * @return 格式化后的当前时间字符串
     */
    public static String getCurrentTimeString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    /**
     * 获取当前时间戳
     * @return 当前时间戳(毫秒)
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
    
    /**
     * 主方法 - 演示如何使用
     */
    public static void main(String[] args) {
        // 方式1: 打印默认格式的当前时间
        printCurrentTime();
        
        // 方式2: 打印自定义格式的当前时间
        printCurrentTime("yyyy年MM月dd日 HH:mm:ss");
        
        // 方式3: 获取当前时间字符串
        String timeStr = getCurrentTimeString();
        System.out.println("获取的时间字符串: " + timeStr);
        
        // 方式4: 获取当前时间戳
        long timestamp = getCurrentTimestamp();
        System.out.println("当前时间戳: " + timestamp);
    }
}
