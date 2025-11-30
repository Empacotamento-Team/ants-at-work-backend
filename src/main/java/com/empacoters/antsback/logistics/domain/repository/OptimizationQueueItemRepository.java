package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;

import java.util.List;

public interface OptimizationQueueItemRepository {
    List<OptimizationQueueItem> findAll();
    OptimizationQueueItem findById(Long id);
    OptimizationQueueItem findFirstPending();
    OptimizationQueueItem save(OptimizationQueueItem optimizationQueueItem);
    void delete(Long id);
}
