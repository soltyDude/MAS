package com.colony.mas_5.repository;

import com.colony.mas_5.entity.EnergySource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {

    @EntityGraph("EnergySource.withDrones")
    Optional<EnergySource> findGraphById(Long id);
}
