package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.infrastructure.entity.OptimizationQueueItemEntity;

public class OptimizationQueueItemMapper {
    public static OptimizationQueueItem toDomain(OptimizationQueueItemEntity item) {
        if (item == null) return null;
        return new OptimizationQueueItem(
            item.getId(), item.getStatus(),
            item.getAttempts(), item.getRequestData(),
            item.getCreatedAt(), item.getUpdatedAt()
        );
    }

    public static OptimizationQueueItemEntity toEntity(OptimizationQueueItem item) {
        if (item == null) return null;
        return new OptimizationQueueItemEntity(
            item.id(), item.status(),
            item.attempts(), item.requestData(),
            item.createdAt(), item.updatedAt()
        );
    }
}
