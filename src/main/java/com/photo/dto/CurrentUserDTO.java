package com.photo.dto;

/**
 * 当前用户信息响应DTO
 */
public class CurrentUserDTO {
    
    private String username;
    private Boolean authenticated;
    
    public CurrentUserDTO() {
    }
    
    public CurrentUserDTO(String username, Boolean authenticated) {
        this.username = username;
        this.authenticated = authenticated;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Boolean getAuthenticated() {
        return authenticated;
    }
    
    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }
}
