package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import com.empacoters.antsback.logistics.infrastructure.mapper.TruckMapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
    public List<Truck> byFleetIdAndStatus(Optional<Long> fleetId, Optional<TruckStatus> status) {
        if (fleetId.isEmpty() && status.isEmpty()) {
            return all();
        }

        List<TruckEntity> entities;

        if (fleetId.isPresent() && status.isPresent()) {
            entities = springDataTruckRepository.findByFleetIdAndStatus(fleetId.get(), status.get());
        } else if (fleetId.isPresent()) {
            entities = springDataTruckRepository.findByFleetId(fleetId.get());
        } else {
            entities = springDataTruckRepository.findByStatus(status.get());
        }

        return entities.stream().map(TruckMapper::toDomain).collect(Collectors.toList());

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
