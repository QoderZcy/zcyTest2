package com.photo.repository;

import com.photo.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 博客数据访问接口
 * 提供博客数据的CRUD操作
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    
    /**
     * 按创建时间倒序查询所有博客
     * @return 博客列表
     */
    List<Blog> findAllByOrderByCreatedTimeDesc();
    
    /**
     * 根据分类查询博客
     * @param category 分类名称
     * @return 博客列表
     */
    List<Blog> findByCategoryOrderByCreatedTimeDesc(String category);
    
    /**
     * 根据作者查询博客
     * @param author 作者名称
     * @return 博客列表
     */
    List<Blog> findByAuthorOrderByCreatedTimeDesc(String author);
}
