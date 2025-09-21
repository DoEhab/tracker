package com.ebu.tracker.entity;

import java.time.LocalDate;
import java.util.Date;

public class ProjectDashboardDTO {
    private Long ticketCount;
    private Long teamSize;
    private int checklistProgress;
    private Boolean onTrack;
    private Date frontendStartDate;
    private Date frontendEndDate;
    private Date backendStartDate;
    private Date backendEndDate;
    private String color;

    // Getters and Setters
    public Long getTicketCount() {
        return ticketCount;
    }           
    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
    public Long getTeamSize() {
        return teamSize;
    }   
    public void setTeamSize(Long teamSize) {
        this.teamSize = teamSize;
    }
    public int getChecklistProgress() {
        return checklistProgress;
    }
    public void setChecklistProgress(int checklistProgress) {
        this.checklistProgress = checklistProgress;
    }
    public Boolean getOnTrack() {
        return onTrack;
    }
    public void setOnTrack(Boolean onTrack) {
        this.onTrack = onTrack;
    }
    public Date getFrontendStartDate() {
        return frontendStartDate;
    }
    public void setFrontendStartDate(Date frontendStartDate) {
        this.frontendStartDate = frontendStartDate;
    }
    public Date getFrontendEndDate() {
        return frontendEndDate;
    }
    public void setFrontendEndDate(Date frontendEndDate) {
        this.frontendEndDate = frontendEndDate;
    }
    public Date getBackendStartDate() {
        return backendStartDate;
    }
    public void setBackendStartDate(Date backendStartDate) {
        this.backendStartDate = backendStartDate;
    }
    public Date getBackendEndDate() {
        return backendEndDate;
    }
    public void setBackendEndDate(Date backendEndDate) {
        this.backendEndDate = backendEndDate;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

}
