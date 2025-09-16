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
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private ChecklistItemRepository checklistRepository;
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        List<Project> projects = projectRepository.findAll();
        Map<String, Object> stats = new HashMap<>();
        
        for (Project project : projects) {
            Map<String, Object> projectStats = new HashMap<>();
            
            Long ticketCount = ticketRepository.countTicketsByProjectId(project.getId());
            Long uniqueAssignees = ticketRepository.countUniqueAssigneesByProjectId(project.getId());
            Long completedChecklist = checklistRepository.countCompletedItemsByProjectId(project.getId());
            Long totalChecklist = checklistRepository.countTotalItemsByProjectId(project.getId());
            
            projectStats.put("ticketCount", ticketCount);
            projectStats.put("teamSize", uniqueAssignees);
            projectStats.put("checklistProgress", totalChecklist > 0 ? 
                (completedChecklist * 100) / totalChecklist : 0);
            projectStats.put("onTrack", project.getOnTrack());
            projectStats.put("frontendStartDate", project.getFrontendStartDate());
            projectStats.put("frontendEndDate", project.getFrontendEndDate());
            projectStats.put("backendStartDate", project.getBackendStartDate());
            projectStats.put("backendEndDate", project.getBackendEndDate());
            projectStats.put("color", project.getColor());
            
            stats.put(project.getName(), projectStats);
        }
        
        return ResponseEntity.ok(stats);
    }
}

