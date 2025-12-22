package com.photo.dto;

import java.time.LocalDateTime;

/**
 * 用户信息DTO
 */
public class UserDTO {
    
    private Long id;
    private String username;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdTime;
    
    public UserDTO() {
    }
    
    public UserDTO(Long id, String username, Boolean enabled, Boolean accountNonLocked, 
                   LocalDateTime lastLoginTime, LocalDateTime createdTime) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.lastLoginTime = lastLoginTime;
        this.createdTime = createdTime;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }
    
    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
