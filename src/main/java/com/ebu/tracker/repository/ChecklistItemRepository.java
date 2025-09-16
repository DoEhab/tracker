package com.ebu.tracker.repository;

import com.ebu.tracker.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    List<ChecklistItem> findByProjectId(Long projectId);

    @Query("SELECT COUNT(c) FROM ChecklistItem c WHERE c.project.id = :projectId AND c.completed = true")
    Long countCompletedItemsByProjectId(Long projectId);

    @Query("SELECT COUNT(c) FROM ChecklistItem c WHERE c.project.id = :projectId")
    Long countTotalItemsByProjectId(Long projectId);
}
