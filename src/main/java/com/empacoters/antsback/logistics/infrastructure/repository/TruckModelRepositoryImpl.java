package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.TruckModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckModelRepositoryImpl implements TruckModelRepository {
    private final SpringDataTruckModelRepository modelRepository;

    public TruckModelRepositoryImpl(SpringDataTruckModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<TruckModel> findAll() {
        return this.modelRepository.findAll().stream()
            .map(TruckModelMapper::toDomain)
            .toList();
    }

    @Override
    public TruckModel findById(Long id) {
        return this.modelRepository.findById(id)
            .map(TruckModelMapper::toDomain)
            .orElse(null);
    }

    @Override
    public TruckModel save(TruckModel family) {
        var saved = this.modelRepository.save(TruckModelMapper.toEntity(family));
        return TruckModelMapper.toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        this.modelRepository.deleteById(id);
    }
}
