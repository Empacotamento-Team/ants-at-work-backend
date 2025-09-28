package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record TruckDTO(
        Long id,
        String plate,
        Integer maximumCapacity,
        Float internalVolume,
        Set<TruckType> types,
        TruckStatus status,
        LocalDate lastRevision,
        Float currentMileage,
        String details,
        List<MaintenanceRecord> maintenanceHistory

) { }
