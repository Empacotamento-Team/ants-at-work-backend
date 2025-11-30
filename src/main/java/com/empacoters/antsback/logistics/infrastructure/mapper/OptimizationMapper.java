package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.infrastructure.entity.OptimizationEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.OptimizationQueueItemEntity;

public class OptimizationMapper {
    public static Optimization toDomain(OptimizationEntity optimizationEntity) {
        return new Optimization(
            optimizationEntity.getId(), optimizationEntity.getOptimizationQueueItem().getId(),
            optimizationEntity.getSolverStatus(), optimizationEntity.getTerminationCondition(),
            optimizationEntity.isFoundSolution(), optimizationEntity.getContainersUsed(),
            optimizationEntity.getFamilyPenality(), optimizationEntity.getGravityCenterDeviation(),
            optimizationEntity.getCreatedAt()
        );
    }

    public static OptimizationEntity toEntity(Optimization optimization) {
        var queueItem = new OptimizationQueueItemEntity(optimization.optimizationQueueItemId(), null, null, null, null, null);

        return new OptimizationEntity(
            optimization.id(), optimization.solverStatus(), optimization.terminationCondition(),
            optimization.foundSolution(), optimization.containersUsed(), optimization.familyPenality(),
            optimization.gravityCenterDeviation(), optimization.createdAt(), queueItem
        );
    }
}
