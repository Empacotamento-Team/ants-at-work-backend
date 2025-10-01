package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record TruckResponseDTO(
        Long id,
        String plate,
        Integer maximumCapacity,
        Double internalHeight,
        Double internalWidth,
        Double internalLength,
        Set<TruckType> types,
        TruckStatus status,
        LocalDate lastRevision,
        Float currentMileage,
        String details,
        List<MaintenanceRecordDTO> maintenanceHistory
) { }
