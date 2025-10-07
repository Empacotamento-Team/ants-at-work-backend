package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

public record TruckUpdateDTO(
        String plate,
        Integer maximumCapacity,
        Double internalHeight,
        Double internalWidth,
        Double internalLength,
        TruckType type,
        TruckStatus status,
        Double currentMileage,
        String details,
        String maintenanceNote
) { }
