package com.photo.controller;

import com.photo.dto.NoteDTO;
import com.photo.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 便签控制器
 * 处理便签相关的所有HTTP请求
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService noteService;
    
    /**
     * 便签列表首页
     * GET /notes
     */
    @GetMapping("/notes")
    public String listNotes(Model model) {
        log.info("访问便签列表页面");
        List<NoteDTO> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "notes/list";
    }
    
    /**
     * 显示新建便签表单
     * GET /notes/new
     */
    @GetMapping("/notes/new")
    public String showCreateForm(Model model) {
        log.info("显示新建便签表单");
        model.addAttribute("note", new NoteDTO());
        model.addAttribute("isNew", true);
        return "notes/edit";
    }
    
    /**
     * 创建便签
     * POST /notes
     */
    @PostMapping("/notes")
    public String createNote(@RequestParam String title,
                            @RequestParam String content,
                            RedirectAttributes redirectAttributes) {
        log.info("处理创建便签请求，标题: {}", title);
        
        try {
            NoteDTO note = noteService.createNote(title, content);
            redirectAttributes.addFlashAttribute("message", "便签创建成功");
            return "redirect:/notes/" + note.getId();
        } catch (Exception e) {
            log.error("创建便签失败", e);
            redirectAttributes.addFlashAttribute("error", "创建便签失败: " + e.getMessage());
            return "redirect:/notes/new";
        }
    }
    
    /**
     * 便签详情页面
     * GET /notes/{id}
     */
    @GetMapping("/notes/{id}")
    public String showNote(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("访问便签详情页面，ID: {}", id);
        
        return noteService.getNoteById(id)
                .map(note -> {
                    model.addAttribute("note", note);
                    return "notes/detail";
                })
                .orElseGet(() -> {
                    log.warn("便签不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "便签不存在");
                    return "redirect:/";
                });
    }
    
    /**
     * 显示编辑便签表单
     * GET /notes/{id}/edit
     */
    @GetMapping("/notes/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("显示编辑便签表单，ID: {}", id);
        
        return noteService.getNoteById(id)
                .map(note -> {
                    model.addAttribute("note", note);
                    model.addAttribute("isNew", false);
                    return "notes/edit";
                })
                .orElseGet(() -> {
                    log.warn("便签不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "便签不存在");
                    return "redirect:/";
                });
    }
    
    /**
     * 更新便签
     * POST /notes/{id}
     */
    @PostMapping("/notes/{id}")
    public String updateNote(@PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam String content,
                            RedirectAttributes redirectAttributes) {
        log.info("处理更新便签请求，ID: {}", id);
        
        return noteService.updateNote(id, title, content)
                .map(note -> {
                    redirectAttributes.addFlashAttribute("message", "便签更新成功");
                    return "redirect:/notes/" + note.getId();
                })
                .orElseGet(() -> {
                    log.warn("便签不存在，ID: {}", id);
                    redirectAttributes.addFlashAttribute("error", "便签不存在");
                    return "redirect:/";
                });
    }
    
    /**
     * 删除便签
     * POST /notes/{id}/delete
     */
    @PostMapping("/notes/{id}/delete")
    public String deleteNote(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("处理删除便签请求，ID: {}", id);
        
        if (noteService.deleteNote(id)) {
            redirectAttributes.addFlashAttribute("message", "便签删除成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "便签不存在");
        }
        
        return "redirect:/";
    }
    
    /**
     * Markdown预览API
     * POST /api/preview
     */
    @PostMapping("/api/preview")
    @ResponseBody
    public String previewMarkdown(@RequestBody String markdown) {
        log.debug("处理Markdown预览请求");
        return noteService.renderMarkdown(markdown);
    }
}
