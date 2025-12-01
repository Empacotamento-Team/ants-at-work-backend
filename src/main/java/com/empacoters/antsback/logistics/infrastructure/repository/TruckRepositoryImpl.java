package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckModelEntity;
import com.empacoters.antsback.logistics.infrastructure.mapper.TruckMapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TruckRepositoryImpl implements TruckRepository {

    private final SpringDataTruckRepository springDataTruckRepository;
    private final SpringDataTruckModelRepository springDataTruckModelRepository;

    public TruckRepositoryImpl(SpringDataTruckRepository springDataTruckRepository, SpringDataTruckModelRepository springDataTruckModelRepository) {
        this.springDataTruckRepository = springDataTruckRepository;
        this.springDataTruckModelRepository = springDataTruckModelRepository;
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
    public Page<Truck> byFleetIdAndStatus(Long fleetId, TruckStatus status, Pageable pageable) {
        Page<TruckEntity> entitiesPage;

        if (fleetId != null && status != null) {
            entitiesPage = springDataTruckRepository.findByFleetIdAndStatus(fleetId, status, pageable);
        } else if (fleetId != null) {
            entitiesPage = springDataTruckRepository.findByFleetId(fleetId, pageable);
        } else if (status != null) {
            entitiesPage = springDataTruckRepository.findByStatus(status, pageable);
        } else {
            entitiesPage = springDataTruckRepository.findAll(pageable);
        }

        List<Truck> trucks = entitiesPage.getContent().stream()
                .map(TruckMapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(trucks, pageable, entitiesPage.getTotalElements());
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
        var truckEntities = springDataTruckRepository.findTop5ByFleetId(fleetId);
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
        TruckEntity entity = TruckMapper.toEntity(truck);
        
        System.out.println("=== DEBUG SAVE TRUCK ===");
        System.out.println("Truck ID: " + truck.id());
        System.out.println("Truck model: " + (truck.model() != null ? truck.model().id() : "null"));
        System.out.println("Entity ID antes: " + entity.getId());
        
        if (truck.model() != null && truck.model().id() != null) {
            System.out.println("Buscando modelo com ID: " + truck.model().id());
            var modelOptional = springDataTruckModelRepository.findById(truck.model().id());
            if (modelOptional.isPresent()) {
                TruckModelEntity modelEntity = modelOptional.get();
                System.out.println("Modelo encontrado: " + modelEntity.getId() + " - " + modelEntity.getName());
                
                entity.setModel(modelEntity);
                
                System.out.println("Model setado na entity: " + (entity.getModel() != null ? entity.getModel().getId() : "null"));
                System.out.println("Entity model após set: " + (entity.getModel() != null ? "ID=" + entity.getModel().getId() : "null"));
            } else {
                System.out.println("ERRO: Modelo com ID " + truck.model().id() + " não encontrado");
                throw new RuntimeException("Modelo com ID " + truck.model().id() + " não encontrado");
            }
        } else {
            System.out.println("Truck não tem model ou model.id é null");
            entity.setModel(null);
        }
        
        System.out.println("Entity antes de salvar - model_id: " + (entity.getModel() != null ? entity.getModel().getId() : "null"));
        System.out.println("Entity antes de salvar - plate: " + entity.getPlate());
        
        var savedTruck = springDataTruckRepository.save(entity);
        
        System.out.println("Entity após salvar - ID: " + savedTruck.getId());
        System.out.println("Entity após salvar - model_id: " + (savedTruck.getModel() != null ? savedTruck.getModel().getId() : "null"));
        System.out.println("Entity após salvar - model completo: " + (savedTruck.getModel() != null ? savedTruck.getModel().toString() : "null"));
        System.out.println("=== FIM DEBUG ===");
        
        return TruckMapper.toDomain(savedTruck);
    }
    @Override
    public Page<Truck> findAllWithFilters(String plate, TruckType type, TruckStatus status, Long modelId, Pageable pageable) {
        String plateFilter = (plate != null && !plate.trim().isEmpty()) ? plate.trim() : null;
        Page<TruckEntity> entitiesPage = springDataTruckRepository.findAllWithFilters(plateFilter, type, status, modelId, pageable);
        
        List<Truck> trucks = entitiesPage.getContent().stream()
                .map(TruckMapper::toDomain)
                .collect(Collectors.toList());
        
        return new PageImpl<>(trucks, pageable, entitiesPage.getTotalElements());
    }
    
    @Override
    public void delete(Long id) {
        springDataTruckRepository.deleteById(id);
    }
}
