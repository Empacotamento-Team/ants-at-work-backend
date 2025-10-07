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
    public Fleet findById(Long id) {
        var entity = this.springDataFleetRepository.findById(id).orElse(null);
        if (entity != null)
            return FleetMapper.toDomain(entity);
        return null;
    }

    @Override
    public Fleet findByCode(String code) {
        var entity = this.springDataFleetRepository.findByCodigo(code).orElse(null);
        if (entity != null)
            return FleetMapper.toDomain(entity);
        return null;
    }

    @Override
    public Fleet save(Fleet fleetToSave) {
        for (var a : fleetToSave.listTrucks()) {
            System.out.println(a.id());
        }

        var b = FleetMapper.toEntity(fleetToSave);
        for (var c: b.getTrucks()) {
            System.out.println(c);
        }
        var fleetEntity = springDataFleetRepository.save(b);
        return FleetMapper.toDomain(fleetEntity);
    }

    @Override
    public void delete(Long id) {
        springDataFleetRepository.deleteById(id);
    }
}
