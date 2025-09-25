package com.ebu.tracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebu.tracker.entity.Project;
import com.ebu.tracker.entity.ProjectDashboardDTO;
import com.ebu.tracker.entity.TeamMember;
import com.ebu.tracker.entity.TeamMemberStatsDTO;
import com.ebu.tracker.repository.ChecklistItemRepository;
import com.ebu.tracker.repository.ProjectRepository;
import com.ebu.tracker.repository.TicketRepository;

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
public ResponseEntity<Map<String, ProjectDashboardDTO>> getDashboardStats() {
    List<Project> projects = projectRepository.findAll();
    Map<String, ProjectDashboardDTO> stats = new HashMap<>();

    for (Project project : projects) {
    ProjectDashboardDTO dto = new ProjectDashboardDTO();

    int ticketCount = ticketRepository.countTicketsByProjectId(project.getId());
    Long uniqueAssignees = ticketRepository.countUniqueAssigneesByProjectId(project.getId());
    Long completedChecklist = checklistRepository.countCompletedItemsByProjectId(project.getId());
    Long totalChecklist = checklistRepository.countTotalItemsByProjectId(project.getId());

    dto.setTicketCount(ticketCount);
    dto.setTeamSize(uniqueAssignees);
    dto.setChecklistProgress(totalChecklist > 0 ? 
        (int)((completedChecklist * 100) / totalChecklist) : 0);
    dto.setOnTrack(project.getOnTrack());
    dto.setFrontendStartDate(project.getFrontendStartDate());
    dto.setFrontendEndDate(project.getFrontendEndDate());
    dto.setBackendStartDate(project.getBackendStartDate());
    dto.setBackendEndDate(project.getBackendEndDate());
    dto.setColor(project.getColor());

    // Add team member stats
    List<Object[]> rawStats = ticketRepository.countCompletedTicketsByAssignee(project.getId());
    List<TeamMemberStatsDTO> teamStats = rawStats.stream()
            .map(r -> new TeamMemberStatsDTO(((TeamMember) r[0]).getName(), (Long) r[1]))
            .toList();
    dto.setTeamMemberStats(teamStats);

    String key = (project.getName() != null && !project.getName().isEmpty()) 
        ? project.getName() 
        : "Unnamed Project " + project.getId();

    stats.put(key, dto);
}


    return ResponseEntity.ok(stats);
    }
}

