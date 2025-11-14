package com.photo.repository;

import com.photo.entity.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 记住我Token数据访问层
 */
@Repository
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Long> {
    
    /**
     * 根据Token值查找
     */
    Optional<RememberMeToken> findByToken(String token);
    
    /**
     * 根据用户名查找所有Token
     */
    List<RememberMeToken> findByUsername(String username);
    
    /**
     * 删除用户的所有Token
     */
    @Modifying
    @Query("DELETE FROM RememberMeToken t WHERE t.username = :username")
    void deleteByUsername(@Param("username") String username);
    
    /**
     * 删除过期的Token
     */
    @Modifying
    @Query("DELETE FROM RememberMeToken t WHERE t.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
    
    /**
     * 删除指定Token
     */
    @Modifying
    @Query("DELETE FROM RememberMeToken t WHERE t.token = :token")
    void deleteByToken(@Param("token") String token);
}
