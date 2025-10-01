package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.FleetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FleetRepositoryImpl implements FleetRepository {
    private final SpringDataFleetRepository springDataFleetRepository;

    public FleetRepositoryImpl(SpringDataFleetRepository fleetRepository) {
        this.springDataFleetRepository = fleetRepository;
    }

    @Override
    public List<Fleet> listAll() {
        var entities = this.springDataFleetRepository.findAll();

        return entities.stream().map(FleetMapper::toDomain).toList();
    }

    @Override
    public Fleet getById(Long id) {
        var entity = this.springDataFleetRepository.findById(id).orElse(null);
        if (entity != null)
            return FleetMapper.toDomain(entity);
        return null;
    }

    @Override
    public Fleet save(Fleet fleetToSave) {
        var fleetEntity = springDataFleetRepository.save(FleetMapper.toEntity(fleetToSave));
        return FleetMapper.toDomain(fleetEntity);
    }

    @Override
    public void delete(Long id) {
        springDataFleetRepository.deleteById(id);
    }
}
