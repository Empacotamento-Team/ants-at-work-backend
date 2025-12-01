package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import com.empacoters.antsback.logistics.infrastructure.mapper.FleetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FleetRepositoryImpl implements FleetRepository {
    private final SpringDataFleetRepository springDataFleetRepository;
    private final SpringDataTruckRepository springDataTruckRepository;

    public FleetRepositoryImpl(SpringDataFleetRepository fleetRepository, SpringDataTruckRepository springDataTruckRepository) {
        this.springDataFleetRepository = fleetRepository;
        this.springDataTruckRepository = springDataTruckRepository;
    }

    @Override
    public List<Fleet> listAll() {
        var entities = this.springDataFleetRepository.findAll();

        return entities.stream().map(FleetMapper::toDomain).toList();
    }

    @Override
    public Page<Fleet> listAll(Pageable pageable) {
        var entitiesPage = this.springDataFleetRepository.findAll(pageable);
        var fleets = entitiesPage.getContent().stream()
                .map(FleetMapper::toDomain)
                .toList();
        return new PageImpl<>(fleets, pageable, entitiesPage.getTotalElements());
    }

    @Override
    public Page<Fleet> listAllWithFilters(String name, String truckPlate, Pageable pageable) {
        String nameFilter = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        String truckPlateFilter = (truckPlate != null && !truckPlate.trim().isEmpty()) ? truckPlate.trim() : null;
        var entitiesPage = this.springDataFleetRepository.findAllWithFilters(nameFilter, truckPlateFilter, pageable);
        var fleets = entitiesPage.getContent().stream()
                .map(FleetMapper::toDomain)
                .toList();
        return new PageImpl<>(fleets, pageable, entitiesPage.getTotalElements());
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
        var fleet = FleetMapper.toEntity(fleetToSave);
        var fleetTrucksIds = fleet.getTrucks().stream().map(TruckEntity::getId).toList();

        if (fleet.getTrucks() != null) {
            List<TruckEntity> trucks = springDataTruckRepository.findByIdIn(fleetTrucksIds);
            trucks.forEach(truck -> truck.setFleet(fleet));
            fleet.setTrucks(trucks);
        }

        var fleetEntity = springDataFleetRepository.save(fleet);
        return FleetMapper.toDomain(fleetEntity);
    }

    @Override
    public void delete(Long id) {
        springDataFleetRepository.deleteById(id);
    }
}
