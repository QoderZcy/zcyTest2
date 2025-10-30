package com.photo.exception;

/**
 * 存储空间不足异常
 */
public class StorageFullException extends FileException {
    
    public StorageFullException(String message) {
        super(message);
    }
    
    public StorageFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
