package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.OptimizationStatus;
import com.empacoters.antsback.logistics.infrastructure.entity.OptimizationQueueItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataOptimizationQueueItemRepository extends JpaRepository<OptimizationQueueItemEntity, Long> {
    Optional<OptimizationQueueItemEntity> findFirstByStatusOrderByCreatedAtAsc(OptimizationStatus status);

    List<OptimizationQueueItemEntity> findAllByStatusOrderByCreatedAtAsc(OptimizationStatus status);
}
