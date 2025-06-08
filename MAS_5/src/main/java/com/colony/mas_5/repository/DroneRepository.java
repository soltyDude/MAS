package com.colony.mas_5.repository;

import com.colony.mas_5.entity.Drone;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {


}
