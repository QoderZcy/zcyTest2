package com.photo.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数生成辅助类
 * 提供各种随机数据生成功能
 */
public class RandomHelper {
    
    private static final Random random = new Random();
    
    /**
     * 生成指定范围内的随机整数
     * 
     * @param min 最小值(包含)
     * @param max 最大值(不包含)
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }
    
    /**
     * 生成随机UUID字符串
     * 
     * @return UUID字符串
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 生成指定长度的随机字符串
     * 
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * 生成随机布尔值
     * 
     * @return 随机布尔值
     */
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }
}
