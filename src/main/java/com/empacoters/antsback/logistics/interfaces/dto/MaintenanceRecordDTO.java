package com.empacoters.antsback.logistics.interfaces.dto;

import java.time.LocalDate;

public record MaintenanceRecordDTO(
        Long id,
        LocalDate date,
        String description
) { }
