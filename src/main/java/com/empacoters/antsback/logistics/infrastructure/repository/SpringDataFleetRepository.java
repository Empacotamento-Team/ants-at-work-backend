package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.FleetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataFleetRepository extends JpaRepository<FleetEntity, Long> {
    Optional<FleetEntity> findByCodigo(String codigo);
}
