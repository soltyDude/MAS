package com.colony.mas_5.repository;

import com.colony.mas_5.entity.MissionAssignment;
import com.colony.mas_5.entity.MissionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionAssignmentRepository extends JpaRepository<MissionAssignment, MissionAssignmentId> {}
