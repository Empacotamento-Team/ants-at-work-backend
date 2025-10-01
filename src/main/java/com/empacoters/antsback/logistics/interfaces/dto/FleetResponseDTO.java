package com.empacoters.antsback.logistics.interfaces.dto;

public record FleetResponseDTO(
        Long id,
        String name,
        String code,
        Integer trucksQuantity,
        Integer activeTrucks,
        Integer underMaintenanceTrucks,
        Double averageCapacity,
        TruckResponseDTO[] trucksSummary
) {}
