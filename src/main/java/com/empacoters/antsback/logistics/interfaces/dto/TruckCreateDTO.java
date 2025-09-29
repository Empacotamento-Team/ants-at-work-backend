package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

import java.util.Set;

public record TruckCreateDTO(
        String plate,
        Integer maximumCapacity,
        Float internalVolume,
        Set<TruckType> types,
        TruckStatus status,
        Float currentMileage,
        String details,
        String maintenanceNote
) { }
