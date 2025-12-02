package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.domain.repository.OptimizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOptimizationsUseCase {
    private final OptimizationRepository optimizationRepository;

    public ListOptimizationsUseCase(OptimizationRepository optimizationRepository) {
        this.optimizationRepository = optimizationRepository;
    }
    
    public List<Optimization> execute() {
        return optimizationRepository.findAll();
    }
}
