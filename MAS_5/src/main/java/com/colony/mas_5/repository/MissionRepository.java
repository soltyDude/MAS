package com.colony.mas_5.repository;

import com.colony.mas_5.entity.Mission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    @EntityGraph("Mission.withAssignments")
    Optional<Mission> findGraphById(Long id);
}
