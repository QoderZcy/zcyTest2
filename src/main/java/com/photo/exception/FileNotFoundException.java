package com.photo.exception;

/**
 * 文件未找到异常
 */
public class FileNotFoundException extends FileException {
    
    public FileNotFoundException(String message) {
        super(message);
    }
    
    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
