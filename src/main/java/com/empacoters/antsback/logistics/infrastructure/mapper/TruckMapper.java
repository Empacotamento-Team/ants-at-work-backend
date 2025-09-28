package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.infrastructure.entity.MaintenanceRecordEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TruckMapper {

    public static TruckEntity toEntity(Truck truck) {
        if (truck == null) return null;

        TruckEntity truckEntity = new TruckEntity(
                truck.id(),
                truck.plate(),
                truck.maximumCapacity(),
                truck.internalVolume(),
                truck.types(),
                truck.status(),
                truck.lastRevision(),
                truck.currentMileage(),
                truck.details(),
                null
        );

        List<MaintenanceRecordEntity> maintenanceEntities = truck.maintenanceHistory().stream()
                .map(record -> MaintenanceRecordMapper.toEntity(record, truckEntity))
                .collect(Collectors.toList());

        truckEntity.getMaintenanceHistory().addAll(maintenanceEntities);

        return truckEntity;
    }

    public static Truck toDomain(TruckEntity truckEntity) {
        if (truckEntity == null) return null;

        List<MaintenanceRecord> maintenanceRecords = truckEntity.getMaintenanceHistory().stream()
                .map(MaintenanceRecordMapper::toDomain)
                .collect(Collectors.toList());

        Truck truck = new Truck(
                truckEntity.getId(),
                truckEntity.getPlate(),
                truckEntity.getMaximumCapacity(),
                truckEntity.getInternalVolume(),
                truckEntity.getTypes(),
                truckEntity.getStatus(),
                truckEntity.getLastRevision(),
                truckEntity.getCurrentMileage(),
                truckEntity.getDetails()
        );

        maintenanceRecords.forEach(truck::addMaintenceRecord);

        return truck;
    }
}
