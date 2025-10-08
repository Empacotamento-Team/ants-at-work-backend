package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Fleet;

public record FleetResponseDTO(
        Long id,
        String name,
        String code,
        Integer trucksQuantity,
        Integer activeTrucks,
        Integer underMaintenanceTrucks,
        Double averageCapacity,
        TruckResponseDTO[] trucksSummary
) {
    public static FleetResponseDTO fromFleet(Fleet fleet) {
        var trucks = !fleet.listTrucks().isEmpty()
            ? fleet.listTrucks().stream().map(TruckResponseDTO::fromTruck).toArray(TruckResponseDTO[]::new)
            : new TruckResponseDTO[0];

        return new FleetResponseDTO(
            fleet.id(),
            fleet.name(),
            fleet.codigo(),
            fleet.trucksCount(),
            fleet.activeTrucksCount(),
            fleet.underMaintenanceTrucksCount(),
            fleet.calculateAverageCapacity(),
            trucks
        );
    }
}
