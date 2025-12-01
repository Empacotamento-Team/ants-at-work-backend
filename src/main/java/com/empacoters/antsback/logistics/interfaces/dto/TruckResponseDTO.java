package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

import java.time.LocalDate;
import java.util.List;

public record TruckResponseDTO(
        Long id,
        String plate,
        Double maximumCapacity,
        Double internalHeight,
        Double internalWidth,
        Double internalLength,
        TruckType type,
        TruckStatus status,
        LocalDate lastRevision,
        Double currentMileage,
        String details,
        List<MaintenanceRecordDTO> maintenanceHistory,
        TruckModel model
) {
    public static TruckResponseDTO fromTruck(Truck truck) {
        if (truck == null)
            return null;
        
        var dimensions = truck.internalDimensions();
        Double height = dimensions != null ? dimensions.height() : null;
        Double width = dimensions != null ? dimensions.width() : null;
        Double length = dimensions != null ? dimensions.length() : null;
        
        return new TruckResponseDTO(
            truck.id(),
            truck.plate(),
            truck.maximumCapacity(),
            height,
            width,
            length,
            truck.type(),
            truck.status(),
            truck.lastRevision(),
            truck.currentMileage(),
            truck.details(),
            truck.maintenanceHistory().stream().map(MaintenanceRecordDTO::fromRecord).toList(),
            truck.model()
        );
    }
}
