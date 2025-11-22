package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.domain.repository.LoadRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.LoadMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoadRepositoryImpl implements LoadRepository {
    private final SpringDataLoadRepository springDataLoadRepository;

    public LoadRepositoryImpl(SpringDataLoadRepository springDataLoadRepository) {
        this.springDataLoadRepository = springDataLoadRepository;
    }

    @Override
    public List<Load> findAll() {
        return springDataLoadRepository.findAll()
                .stream()
                .map(LoadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Load findById(Long id) {
        var entity = springDataLoadRepository.findById(id).orElse(null);
        return LoadMapper.toDomain(entity);
    }

    @Override
    public List<Load> findByShipmentId(Long shipmentId) {
        return springDataLoadRepository.findByShipmentId(shipmentId)
                .stream()
                .map(LoadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Load> findByTruckId(Long truckId) {
        return springDataLoadRepository.findByRelatedTruckId(truckId)
                .stream()
                .map(LoadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Load save(Load load) {
        var savedLoad = springDataLoadRepository.save(LoadMapper.toEntity(load));
        return LoadMapper.toDomain(savedLoad);
    }

    @Override
    public void delete(Long id) {
        springDataLoadRepository.deleteById(id);
    }
}