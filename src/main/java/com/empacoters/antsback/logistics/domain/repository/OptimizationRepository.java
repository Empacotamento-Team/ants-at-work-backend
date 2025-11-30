package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Optimization;

import java.util.List;

public interface OptimizationRepository {
    List<Optimization> findAll();
    Optimization findById(Long id);
    Optimization save(Optimization optimization);
    void delete(Long id);
}
