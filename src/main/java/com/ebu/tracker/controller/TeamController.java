package com.ebu.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebu.tracker.entity.TeamMember;
import com.ebu.tracker.repository.TeamMemberRepository;


@RestController
@RequestMapping("/api/team")
@CrossOrigin(origins = "*")
public class TeamController {
    
    @Autowired
    private TeamMemberRepository teamRepository;
    
    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        return teamRepository.findAll();
    }
    
    @PostMapping
    public TeamMember createTeamMember(@RequestBody TeamMember member) {
        return teamRepository.save(member);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable Long id, 
            @RequestBody TeamMember member) {
        if (!teamRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        member.setId(id);
        return ResponseEntity.ok(teamRepository.save(member));
    }
}
