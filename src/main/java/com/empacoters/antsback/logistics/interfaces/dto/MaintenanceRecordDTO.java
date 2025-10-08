package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;

import java.time.LocalDate;

public record MaintenanceRecordDTO(
        Long id,
        LocalDate date,
        String description
) {
    public static MaintenanceRecordDTO fromRecord(MaintenanceRecord record) {
        return new MaintenanceRecordDTO(
            record.id(),
            record.date(),
            record.description()
        );
    }
}
