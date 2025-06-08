package com.colony.mas_5.repository;

import com.colony.mas_5.entity.HabitatUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitatUnitRepository extends JpaRepository<HabitatUnit, Long> {

    @EntityGraph("HabitatUnit.withRooms")
    Optional<HabitatUnit> findGraphById(Long id);
}
