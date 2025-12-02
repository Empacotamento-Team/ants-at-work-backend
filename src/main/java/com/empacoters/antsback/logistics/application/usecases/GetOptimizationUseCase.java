package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.domain.repository.OptimizationRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOptimizationUseCase {
    private final OptimizationRepository optimizationRepository;

    public GetOptimizationUseCase(OptimizationRepository optimizationRepository) {
        this.optimizationRepository = optimizationRepository;
    }

    public Optimization execute(Long id) {
        return this.optimizationRepository.findById(id);
    }
}
