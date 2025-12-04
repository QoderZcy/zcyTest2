package com.photo.service;

import com.photo.dto.NoteDTO;
import com.photo.entity.Note;
import com.photo.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 便签业务服务类
 * 提供便签的增删改查业务逻辑和Markdown处理
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {
    
    private final NoteRepository noteRepository;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 获取所有便签列表
     * @return 便签DTO列表
     */
    public List<NoteDTO> getAllNotes() {
        log.info("获取所有便签列表");
        List<Note> notes = noteRepository.findAllByOrderByCreatedTimeDesc();
        return notes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据ID获取便签
     * @param id 便签ID
     * @return 便签DTO
     */
    public Optional<NoteDTO> getNoteById(Long id) {
        log.info("获取便签详情，ID: {}", id);
        return noteRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * 创建新便签
     * @param title 标题
     * @param content 内容
     * @return 创建的便签DTO
     */
    @Transactional
    public NoteDTO createNote(String title, String content) {
        log.info("创建新便签，标题: {}", title);
        
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        
        Note savedNote = noteRepository.save(note);
        log.info("便签创建成功，ID: {}", savedNote.getId());
        
        return convertToDTO(savedNote);
    }
    
    /**
     * 更新便签
     * @param id 便签ID
     * @param title 新标题
     * @param content 新内容
     * @return 更新后的便签DTO
     */
    @Transactional
    public Optional<NoteDTO> updateNote(Long id, String title, String content) {
        log.info("更新便签，ID: {}", id);
        
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(title);
                    note.setContent(content);
                    Note updatedNote = noteRepository.save(note);
                    log.info("便签更新成功，ID: {}", updatedNote.getId());
                    return convertToDTO(updatedNote);
                });
    }
    
    /**
     * 删除便签
     * @param id 便签ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteNote(Long id) {
        log.info("删除便签，ID: {}", id);
        
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            log.info("便签删除成功，ID: {}", id);
            return true;
        }
        
        log.warn("便签不存在，无法删除，ID: {}", id);
        return false;
    }
    
    /**
     * 将Markdown文本渲染为HTML
     * @param markdown Markdown文本
     * @return HTML文本
     */
    public String renderMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        
        try {
            org.commonmark.node.Node document = markdownParser.parse(markdown);
            return htmlRenderer.render(document);
        } catch (Exception e) {
            log.error("Markdown渲染失败", e);
            return markdown;
        }
    }
    
    /**
     * 生成内容摘要
     * @param content 完整内容
     * @return 摘要文本（前100字符）
     */
    private String generatePreview(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        // 移除Markdown标记符号
        String plainText = content.replaceAll("[#*`\\[\\]()\\-_>]", "").trim();
        
        if (plainText.length() <= 100) {
            return plainText;
        }
        
        return plainText.substring(0, 100) + "...";
    }
    
    /**
     * 将Note实体转换为NoteDTO
     * @param note Note实体
     * @return NoteDTO对象
     */
    private NoteDTO convertToDTO(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .htmlContent(renderMarkdown(note.getContent()))
                .contentPreview(generatePreview(note.getContent()))
                .createdTime(note.getCreatedTime().format(DATE_TIME_FORMATTER))
                .updatedTime(note.getUpdatedTime().format(DATE_TIME_FORMATTER))
                .build();
    }
}
