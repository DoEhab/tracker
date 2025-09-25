package com.ebu.tracker.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.ebu.tracker.entity.TeamDTO;
import com.ebu.tracker.entity.TeamMember;
import com.ebu.tracker.repository.TeamMemberRepository;

@RestController
@RequestMapping("/api/team")
@CrossOrigin(origins = "*")
public class TeamController {
    
    @Autowired
    private TeamMemberRepository teamRepository;

    @GetMapping
    public List<TeamDTO> getAllTeamMembers() {
        return teamRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @PostMapping
    public TeamDTO createTeamMember(@RequestBody TeamDTO teamDTO) {
        TeamMember member = convertToEntity(teamDTO);
        TeamMember savedMember = teamRepository.save(member);
        return convertToDTO(savedMember);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeamMember(@PathVariable Long id, 
            @RequestBody TeamDTO teamDTO) {
        if (!teamRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        TeamMember member = convertToEntity(teamDTO);
        member.setId(id);
        TeamMember updatedMember = teamRepository.save(member);
        return ResponseEntity.ok(convertToDTO(updatedMember));
    }

    // Helper methods to convert between DTO and Entity
    private TeamDTO convertToDTO(TeamMember member) {
        return new TeamDTO(member.getId(), member.getName(), member.getRole());
    }

    private TeamMember convertToEntity(TeamDTO teamDTO) {
        TeamMember member = new TeamMember();
        member.setId(teamDTO.getId());
        member.setName(teamDTO.getName());
        member.setRole(teamDTO.getRole());
        return member;
    }
}