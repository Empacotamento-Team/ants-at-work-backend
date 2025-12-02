package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.domain.repository.OptimizationQueueItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOptimizationQueueItemsUseCase {
    private final OptimizationQueueItemRepository optimizationQueueItemRepository;

    public ListOptimizationQueueItemsUseCase(OptimizationQueueItemRepository optimizationQueueItemRepository) {
        this.optimizationQueueItemRepository = optimizationQueueItemRepository;
    }

    public List<OptimizationQueueItem> execute() {
        return optimizationQueueItemRepository.findAll();
    }
}
