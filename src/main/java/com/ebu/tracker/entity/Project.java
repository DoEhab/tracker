package com.ebu.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import java.util.List;



@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    private String description;
    private String color;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date frontendStartDate;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date frontendEndDate;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date backendStartDate;
    
    @Temporal(TemporalType.DATE)
    private java.util.Date backendEndDate;
    
    private Boolean onTrack = true;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ChecklistItem> checklist;
    
    // Constructors
    public Project() {}
    
    public Project(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public java.util.Date getFrontendStartDate() { return frontendStartDate; }
    public void setFrontendStartDate(java.util.Date frontendStartDate) { this.frontendStartDate = frontendStartDate; }
    
    public java.util.Date getFrontendEndDate() { return frontendEndDate; }
    public void setFrontendEndDate(java.util.Date frontendEndDate) { this.frontendEndDate = frontendEndDate; }
    
    public java.util.Date getBackendStartDate() { return backendStartDate; }
    public void setBackendStartDate(java.util.Date backendStartDate) { this.backendStartDate = backendStartDate; }
    
    public java.util.Date getBackendEndDate() { return backendEndDate; }
    public void setBackendEndDate(java.util.Date backendEndDate) { this.backendEndDate = backendEndDate; }
    
    public Boolean getOnTrack() { return onTrack; }
    public void setOnTrack(Boolean onTrack) { this.onTrack = onTrack; }
    
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
    
    public List<ChecklistItem> getChecklist() { return checklist; }
    public void setChecklist(List<ChecklistItem> checklist) { this.checklist = checklist; }
}

