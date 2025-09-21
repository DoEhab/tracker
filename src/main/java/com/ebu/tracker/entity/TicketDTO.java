// TicketDTO.java
package com.ebu.tracker.entity;

import java.util.Date;

public class TicketDTO {
    private Long id;
    private String title;
    private String status;
    private String priority;
    private String projectName;
    private String projectColor;
    private String assigneeName;
    private String assigneeRole;
    private Date createdAt;

    // constructor
    public TicketDTO(Long id, String title, String status, String priority,
                     String projectName, String projectColor,
                     String assigneeName, String assigneeRole,
                     Date createdAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.projectName = projectName;
        this.projectColor = projectColor;
        this.assigneeName = assigneeName;
        this.assigneeRole = assigneeRole;
        this.createdAt = createdAt;
    }

    // getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getProjectName() { return projectName; }
    public String getProjectColor() { return projectColor; }
    public String getAssigneeName() { return assigneeName; }
    public String getAssigneeRole() { return assigneeRole; }
    public Date getCreatedAt() { return createdAt; }
}
