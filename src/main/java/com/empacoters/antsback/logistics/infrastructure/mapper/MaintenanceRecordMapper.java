package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;
import com.empacoters.antsback.logistics.infrastructure.entity.MaintenanceRecordEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;

public class MaintenanceRecordMapper {
    public static MaintenanceRecordEntity toEntity(MaintenanceRecord maintenanceRecord, TruckEntity truckEntity){
        if(maintenanceRecord == null || truckEntity == null)
            return null;

        MaintenanceRecordEntity entity = new MaintenanceRecordEntity(
                maintenanceRecord.id(),
                maintenanceRecord.date(),
                maintenanceRecord.description(),
                truckEntity
        );
        return entity;
    }
    public static MaintenanceRecord toDomain(MaintenanceRecordEntity maintenanceRecordEntity){
        if (maintenanceRecordEntity == null)
            return null;

        return new MaintenanceRecord(
                maintenanceRecordEntity.getId(),
                maintenanceRecordEntity.getDate(),
                maintenanceRecordEntity.getDescription()
        );
    }
}
