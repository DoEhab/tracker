package com.ebu.tracker.repository;

import com.ebu.tracker.entity.Ticket;
import com.ebu.tracker.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByProjectId(Long projectId);
    List<Ticket> findByAssigneeId(Long assigneeId);
    List<Ticket> findByStatus(TicketStatus status);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.project.id = :projectId")
    Long countTicketsByProjectId(Long projectId);

    @Query("SELECT COUNT(DISTINCT t.assignee.id) FROM Ticket t WHERE t.project.id = :projectId")
    Long countUniqueAssigneesByProjectId(Long projectId);

    @Query("SELECT t FROM Ticket t WHERE t.assignee.id = :assigneeId ORDER BY t.createdAt DESC")
    List<Ticket> findByAssigneeOrderByCreatedAtDesc(Long assigneeId);
}
