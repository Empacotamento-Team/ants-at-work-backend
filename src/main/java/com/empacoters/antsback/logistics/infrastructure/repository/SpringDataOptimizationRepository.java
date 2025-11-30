package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.OptimizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOptimizationRepository extends JpaRepository<OptimizationEntity, Long> {
}
