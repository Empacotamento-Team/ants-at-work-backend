package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizationRequestDTO(
    Long fleetId,
    Long[] packagesIds
) {}
