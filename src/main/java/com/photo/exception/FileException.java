package com.photo.exception;

/**
 * 文件相关异常基类
 */
public class FileException extends RuntimeException {
    
    public FileException(String message) {
        super(message);
    }
    
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
