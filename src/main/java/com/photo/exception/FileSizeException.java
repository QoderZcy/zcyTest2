package com.photo.exception;

/**
 * 文件大小异常
 */
public class FileSizeException extends FileException {
    
    public FileSizeException(String message) {
        super(message);
    }
    
    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
