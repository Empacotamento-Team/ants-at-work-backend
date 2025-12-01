package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.TruckModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<TruckModel> findAll(Pageable pageable) {
        var entitiesPage = this.modelRepository.findAll(pageable);
        var models = entitiesPage.getContent().stream()
            .map(TruckModelMapper::toDomain)
            .toList();
        return new PageImpl<>(models, pageable, entitiesPage.getTotalElements());
    }

    @Override
    public Page<TruckModel> findAllWithFilters(String name, com.empacoters.antsback.logistics.domain.model.TruckType type, Pageable pageable) {
        String nameFilter = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        var entitiesPage = this.modelRepository.findAllWithFilters(nameFilter, type, pageable);
        var models = entitiesPage.getContent().stream()
            .map(TruckModelMapper::toDomain)
            .toList();
        return new PageImpl<>(models, pageable, entitiesPage.getTotalElements());
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
