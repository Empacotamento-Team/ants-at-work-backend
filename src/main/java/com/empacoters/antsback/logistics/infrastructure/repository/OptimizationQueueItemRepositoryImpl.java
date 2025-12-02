package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.domain.model.OptimizationStatus;
import com.empacoters.antsback.logistics.domain.repository.OptimizationQueueItemRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.OptimizationQueueItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptimizationQueueItemRepositoryImpl implements OptimizationQueueItemRepository {
    public final SpringDataOptimizationQueueItemRepository springDataRepository;

    public OptimizationQueueItemRepositoryImpl(SpringDataOptimizationQueueItemRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<OptimizationQueueItem> findAll() {
        return springDataRepository.findAllOrderByCreatedAtAsc()
            .stream()
            .map(OptimizationQueueItemMapper::toDomain)
            .toList();
    }

    @Override
    public List<OptimizationQueueItem> findAllByStatus(OptimizationStatus status) {
        return springDataRepository.findAllByStatusOrderByCreatedAtAsc(status)
            .stream()
            .map(OptimizationQueueItemMapper::toDomain)
            .toList();
    }

    @Override
    public OptimizationQueueItem findById(Long id) {
        var entityItem = springDataRepository.findById(id).orElse(null);
        return OptimizationQueueItemMapper.toDomain(entityItem);
    }

    @Override
    public OptimizationQueueItem findFirstPending() {
        var entityItem = springDataRepository.findFirstByStatusOrderByCreatedAtAsc(OptimizationStatus.PENDING)
                .orElse(null);
        return OptimizationQueueItemMapper.toDomain(entityItem);
    }

    @Override
    public OptimizationQueueItem save(OptimizationQueueItem optimizationQueueItem) {
        var savingEntity = OptimizationQueueItemMapper.toEntity(optimizationQueueItem);

        var savedEntity = springDataRepository.save(savingEntity);
        return OptimizationQueueItemMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {
        springDataRepository.deleteById(id);
    }
}
