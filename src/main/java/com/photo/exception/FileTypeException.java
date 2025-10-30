package com.photo.exception;

/**
 * 文件类型异常
 */
public class FileTypeException extends FileException {
    
    public FileTypeException(String message) {
        super(message);
    }
    
    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
