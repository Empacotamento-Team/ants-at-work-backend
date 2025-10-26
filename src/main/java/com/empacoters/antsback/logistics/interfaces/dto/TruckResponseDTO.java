package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

import java.time.LocalDate;
import java.util.List;

public record TruckResponseDTO(
        Long id,
        String plate,
        Integer maximumCapacity,
        Double internalHeight,
        Double internalWidth,
        Double internalLength,
        TruckType type,
        TruckStatus status,
        LocalDate lastRevision,
        Double currentMileage,
        String details,
        List<MaintenanceRecordDTO> maintenanceHistory
) {
    public static TruckResponseDTO fromTruck(Truck truck) {
        if (truck == null)
            return null;
        return new TruckResponseDTO(
            truck.id(),
            truck.plate(),
            truck.maximumCapacity(),
            truck.internalDimensions().height(),
            truck.internalDimensions().width(),
            truck.internalDimensions().length(),
            truck.type(),
            truck.status(),
            truck.lastRevision(),
            truck.currentMileage(),
            truck.details(),
            truck.maintenanceHistory().stream().map(MaintenanceRecordDTO::fromRecord).toList()
        );
    }
}
