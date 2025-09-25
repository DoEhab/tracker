package com.ebu.tracker.entity;
// ProjectDTO.java
public class ProjectDTO {
    private Long id;
    private String name;
    private String color;
    private String description;
    // no tickets list, or maybe just ticketCount
    private int ticketCount; 

    // constructor
    public ProjectDTO(Long id, String name, String color, String description, int ticketCount) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.ticketCount = ticketCount;
    }
    // getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public String getDescription() { return description; }
    public int getTicketCount() { return ticketCount; }
    
    //setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public void setDescription(String description) { this.description = description; }
    public void setTicketCount(int ticketCount) { this.ticketCount = ticketCount; }

}
