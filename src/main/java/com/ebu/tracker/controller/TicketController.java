package com.ebu.tracker.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.ebu.tracker.entity.Ticket;
import com.ebu.tracker.entity.TicketDTO;
import com.ebu.tracker.entity.TicketStatus;
import com.ebu.tracker.repository.TicketRepository;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
        .map(t -> new TicketDTO(
            t.getId(),
            t.getTitle(),
            t.getStatus().name(),
            t.getPriority().name(),
            t.getProject() != null ? t.getProject().getName() : null,
            t.getProject() != null ? t.getProject().getColor() : null,
            t.getAssignee() != null ? t.getAssignee().getName() : null,
            t.getAssignee() != null ? t.getAssignee().getRole() : null,
            t.getCreatedAt()
        ))
        .collect(Collectors.toList());

    }

    @GetMapping("/assignee/{assigneeId}")
    public List<TicketDTO> getTicketsByAssignee(@PathVariable Long assigneeId) {
        return ticketRepository.findByAssigneeOrderByCreatedAtDesc(assigneeId).stream()
                .map(ticket -> new TicketDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getStatus().name(),
                ticket.getPriority().name(),
                ticket.getProject() != null ? ticket.getProject().getName() : null,
                ticket.getProject() != null ? ticket.getProject().getColor() : null,
                ticket.getAssignee() != null ? ticket.getAssignee().getName() : null,
                ticket.getAssignee() != null ? ticket.getAssignee().getRole() : null,
                ticket.getCreatedAt()
        ))
                .toList();
    }

    @PostMapping
    public TicketDTO createTicket(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(new java.util.Date());
        ticket.setUpdatedAt(new java.util.Date());
        Ticket saved = ticketRepository.save(ticket);

        return new TicketDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus().name(),
                saved.getPriority().name(),
                saved.getProject() != null ? saved.getProject().getName() : null,
                saved.getProject() != null ? saved.getProject().getColor() : null,
                saved.getAssignee() != null ? saved.getAssignee().getName() : null,
                saved.getAssignee() != null ? saved.getAssignee().getRole() : null,
                saved.getCreatedAt()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        if (!ticketRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        ticket.setUpdatedAt(new java.util.Date());
        Ticket saved = ticketRepository.save(ticket);

        TicketDTO dto = new TicketDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus().name(),
                saved.getPriority().name(),
                saved.getProject() != null ? saved.getProject().getName() : null,
                saved.getProject() != null ? saved.getProject().getColor() : null,
                saved.getAssignee() != null ? saved.getAssignee().getName() : null,
                saved.getAssignee() != null ? saved.getAssignee().getRole() : null,
                saved.getCreatedAt()
        );
        return ResponseEntity.ok(dto);
    }

        @PutMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(@PathVariable Long id,
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
            Ticket saved = ticketRepository.save(ticket);

            TicketDTO dto = new TicketDTO(
                    saved.getId(),
                    saved.getTitle(),
                    saved.getStatus().name(),
                    saved.getPriority().name(),
                    saved.getProject() != null ? saved.getProject().getName() : null,
                    saved.getProject() != null ? saved.getProject().getColor() : null,
                    saved.getAssignee() != null ? saved.getAssignee().getName() : null,
                    saved.getAssignee() != null ? saved.getAssignee().getRole() : null,
                    saved.getCreatedAt()
            );
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
