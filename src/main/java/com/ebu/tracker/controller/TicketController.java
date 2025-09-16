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
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    
    @GetMapping("/assignee/{assigneeId}")
    public List<Ticket> getTicketsByAssignee(@PathVariable Long assigneeId) {
        return ticketRepository.findByAssigneeOrderByCreatedAtDesc(assigneeId);
    }
    
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(new java.util.Date());
        ticket.setUpdatedAt(new java.util.Date());
        return ticketRepository.save(ticket);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        if (!ticketRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        ticket.setUpdatedAt(new java.util.Date());
        return ResponseEntity.ok(ticketRepository.save(ticket));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Ticket> updateTicketStatus(@PathVariable Long id, 
            @RequestBody Map<String, String> statusUpdate) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (!ticketOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Ticket ticket = ticketOpt.get();
        try {
            TicketStatus newStatus = TicketStatus.valueOf(statusUpdate.get("status"));
            ticket.setStatus(newStatus);
            ticket.setUpdatedAt(new java.util.Date());
            return ResponseEntity.ok(ticketRepository.save(ticket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}