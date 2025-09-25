package com.ebu.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebu.tracker.entity.Ticket;
import com.ebu.tracker.entity.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByProjectId(Long projectId);
    List<Ticket> findByAssigneeId(Long assigneeId);
    List<Ticket> findByStatus(TicketStatus status);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.project.id = :projectId")
    int  countTicketsByProjectId(Long projectId);

    @Query("SELECT COUNT(DISTINCT t.assignee.id) FROM Ticket t WHERE t.project.id = :projectId")
    Long countUniqueAssigneesByProjectId(Long projectId);

    @Query("SELECT t FROM Ticket t WHERE t.assignee.id = :assigneeId ORDER BY t.createdAt DESC")
    List<Ticket> findByAssigneeOrderByCreatedAtDesc(Long assigneeId);

    @Query("SELECT t.assignee, COUNT(t) FROM Ticket t WHERE t.project.id = :projectId AND t.status = 'DONE' GROUP BY t.assignee")
    List<Object[]> countCompletedTicketsByAssignee(@Param("projectId") Long projectId);

}
