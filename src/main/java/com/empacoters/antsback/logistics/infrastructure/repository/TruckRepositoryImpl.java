package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import com.empacoters.antsback.logistics.infrastructure.mapper.TruckMapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TruckRepositoryImpl implements TruckRepository {

    private final SpringDataTruckRepository springDataTruckRepository;

    public TruckRepositoryImpl(SpringDataTruckRepository springDataTruckRepository) {
        this.springDataTruckRepository = springDataTruckRepository;
    }

    @Override
    public List<Truck> all() {
        return springDataTruckRepository.findAll()
                .stream()
                .map(TruckMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Truck> byIdIn(List<Long> ids) {
        return springDataTruckRepository.findByIdIn(ids)
                .stream()
                .map(TruckMapper::toDomain)
                .toList();
    }

    @Override
    public List<Truck> byFleetIdAndStatus(Long fleetId, TruckStatus status) {
        if (fleetId == null && status == null) {
            return all();
        }

        List<TruckEntity> entities;

        if (fleetId != null && status != null) {
            entities = springDataTruckRepository.findByFleetIdAndStatus(fleetId, status);
        } else if (fleetId != null) {
            entities = springDataTruckRepository.findByFleetId(fleetId);
        } else {
            entities = springDataTruckRepository.findByStatus(status);
        }

        return entities.stream().map(TruckMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Integer countAllByFleetId(Long fleetId) {
        return springDataTruckRepository.countAllByFleetId(fleetId);
    }

    @Override
    public Integer countAllByFleetIdAndStatus(Long fleetId, TruckStatus status) {
        return springDataTruckRepository.countAllByFleetIdAndStatus(fleetId, status);
    }

    @Override
    public List<Truck> fiveByFleetId(Long fleetId) {
        var truckEntities = springDataTruckRepository.findFiveByFleetId(fleetId);
        return truckEntities.stream().map(TruckMapper::toDomain).toList();
    }

    @Override
    public Truck byId(Long id) {
        var entity = springDataTruckRepository.findById(id).orElse(null);
        return TruckMapper.toDomain(entity);
    }

    @Override
    public Truck byPlate(String plate) {
        var truck = springDataTruckRepository.findByPlate(plate);
        if (truck == null || truck.isEmpty()) {
            return null;
        }

        return TruckMapper.toDomain(truck.getFirst());
    }

    @Override
    @NonNull
    public Truck save(@NonNull Truck truck) {
        var savedTruck = springDataTruckRepository.save(TruckMapper.toEntity(truck));
        return TruckMapper.toDomain(savedTruck);
    }
    @Override
    public void delete(Long id) {
        springDataTruckRepository.deleteById(id);
    }
}
