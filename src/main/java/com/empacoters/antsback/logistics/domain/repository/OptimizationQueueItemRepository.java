package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.domain.model.OptimizationStatus;

import java.util.List;

public interface OptimizationQueueItemRepository {
    List<OptimizationQueueItem> findAll();
    List<OptimizationQueueItem> findAllByStatus(OptimizationStatus status);
    OptimizationQueueItem findById(Long id);
    OptimizationQueueItem findFirstPending();
    OptimizationQueueItem save(OptimizationQueueItem optimizationQueueItem);
    void delete(Long id);
}
