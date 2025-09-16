package com.ebu.tracker.repository;

import com.ebu.tracker.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByNameContainingIgnoreCase(String name);
    TeamMember findByEmail(String email);
}
