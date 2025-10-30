package com.photo.exception;

/**
 * 文件存储异常
 */
public class FileStorageException extends FileException {
    
    public FileStorageException(String message) {
        super(message);
    }
    
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
