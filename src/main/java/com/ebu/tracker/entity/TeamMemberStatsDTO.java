package com.ebu.tracker.entity;

public class TeamMemberStatsDTO {
    private String assignee;
    private Long completedTickets;

    public TeamMemberStatsDTO(String assignee, Long completedTickets) {
        this.assignee = assignee;
        this.completedTickets = completedTickets;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    public Long getCompletedTickets() {
        return completedTickets;
    }
    public void setCompletedTickets(Long completedTickets) {
        this.completedTickets = completedTickets;
    }
}
