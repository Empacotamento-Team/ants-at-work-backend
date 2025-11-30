package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.domain.repository.OptimizationRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.OptimizationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptimizationRepositoryImpl implements OptimizationRepository {
    private final SpringDataOptimizationRepository springDataOptimizationRepository;

    public OptimizationRepositoryImpl(SpringDataOptimizationRepository optimizationRepository) {
        this.springDataOptimizationRepository = optimizationRepository;
    }

    @Override
    public List<Optimization> findAll() {
        var all = springDataOptimizationRepository.findAll();
        return all.stream().map(OptimizationMapper::toDomain).toList();
    }

    @Override
    public Optimization findById(Long id) {
        var entity = springDataOptimizationRepository.findById(id);
        return entity.map(OptimizationMapper::toDomain).orElse(null);
    }

    @Override
    public Optimization save(Optimization optimization) {
        var saved = springDataOptimizationRepository.save(OptimizationMapper.toEntity(optimization));
        return OptimizationMapper.toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        springDataOptimizationRepository.deleteById(id);
    }
}
