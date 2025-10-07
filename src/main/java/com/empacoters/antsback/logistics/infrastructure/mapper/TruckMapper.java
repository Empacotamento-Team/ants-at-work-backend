package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.infrastructure.entity.FleetEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.MaintenanceRecordEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TruckMapper {

    public static TruckEntity toEntity(Truck truck) {
        if (truck == null) return null;

        TruckEntity truckEntity = new TruckEntity();
        truckEntity.setId(truck.id());
        if (truck.fleet().id() != null) {
            FleetEntity fleetEntity = new FleetEntity();
            fleetEntity.setId(truck.fleet().id());
            truckEntity.setFleet(fleetEntity);
        }
        truckEntity.setPlate(truck.plate());
        truckEntity.setMaximumCapacity(truck.maximumCapacity());
        truckEntity.setInternalDimensions(truck.internalDimensions());
        truckEntity.setStatus(truck.status());
        truckEntity.setLastRevision(truck.lastRevision());
        truckEntity.setCurrentMileage(truck.currentMileage());
        truckEntity.setDetails(truck.details());

        List<MaintenanceRecordEntity> maintenanceEntities = truck.maintenanceHistory().stream()
                .map(record -> MaintenanceRecordMapper.toEntity(record, truckEntity))
                .collect(Collectors.toList());

        truckEntity.setMaintenanceHistory(maintenanceEntities);
        return truckEntity;
    }

    public static Truck toDomain(TruckEntity truckEntity) {
        if (truckEntity == null) return null;

        Fleet fleet = null;
        var fleetEntity = truckEntity.getFleet();
        if (fleetEntity != null && fleetEntity.getId() != null) {
            fleet = new Fleet(
                fleetEntity.getId(),
                fleetEntity.getName(),
                fleetEntity.getCodigo(),
                fleetEntity.getPlaceOfOperation(),
                new ArrayList<>()
            );
        }

        List<MaintenanceRecord> maintenanceRecords = truckEntity.getMaintenanceHistory().stream()
                .map(MaintenanceRecordMapper::toDomain)
                .collect(Collectors.toList());

        Truck truck = new Truck(
                truckEntity.getId(),
                truckEntity.getPlate(),
                truckEntity.getMaximumCapacity(),
                truckEntity.getInternalDimensions(),
                truckEntity.getType(),
                truckEntity.getStatus(),
                truckEntity.getLastRevision(),
                truckEntity.getCurrentMileage(),
                truckEntity.getDetails(),
                fleet
        );

        maintenanceRecords.forEach(truck::addMaintenceRecord);
        return truck;
    }
}
