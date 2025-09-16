package com.ebu.tracker.repository;

import com.ebu.tracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Project p WHERE p.onTrack = true")
    List<Project> findOnTrackProjects();

    @Query("SELECT p FROM Project p WHERE p.onTrack = false")
    List<Project> findOffTrackProjects();
}
