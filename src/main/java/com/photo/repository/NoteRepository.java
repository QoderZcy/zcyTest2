package com.photo.repository;

import com.photo.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 便签数据访问接口
 * 提供便签的数据库操作方法
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    /**
     * 查询所有便签，按创建时间倒序排列
     * @return 便签列表
     */
    List<Note> findAllByOrderByCreatedTimeDesc();
}
