package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.FleetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFleetRepository extends JpaRepository<FleetEntity, Long> {
}
