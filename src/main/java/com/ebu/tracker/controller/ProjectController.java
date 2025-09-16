package com.ebu.tracker.controller;

import com.ebu.tracker.entity.*;
import com.ebu.tracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ChecklistItemRepository checklistRepository;
    
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        project.setId(id);
        return ResponseEntity.ok(projectRepository.save(project));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/checklist")
    public List<ChecklistItem> getProjectChecklist(@PathVariable Long id) {
        return checklistRepository.findByProjectId(id);
    }
    
    @PostMapping("/{id}/checklist")
    public ChecklistItem addChecklistItem(@PathVariable Long id, @RequestBody ChecklistItem item) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            item.setProject(project.get());
            ChecklistItem saved = checklistRepository.save(item);
            updateProjectTrackingStatus(id);
            return saved;
        }
        return null;
    }
    
    @PutMapping("/checklist/{itemId}")
    public ResponseEntity<ChecklistItem> updateChecklistItem(@PathVariable Long itemId, 
            @RequestBody ChecklistItem item) {
        if (!checklistRepository.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        item.setId(itemId);
        ChecklistItem saved = checklistRepository.save(item);
        if (saved.getProject() != null) {
            updateProjectTrackingStatus(saved.getProject().getId());
        }
        return ResponseEntity.ok(saved);
    }
    
    private void updateProjectTrackingStatus(Long projectId) {
        Long completed = checklistRepository.countCompletedItemsByProjectId(projectId);
        Long total = checklistRepository.countTotalItemsByProjectId(projectId);
        
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isPresent() && total > 0) {
            Project project = projectOpt.get();
            double completionRate = (double) completed / total;
            project.setOnTrack(completionRate >= 0.7); // 70% completion threshold
            projectRepository.save(project);
        }
    }
}