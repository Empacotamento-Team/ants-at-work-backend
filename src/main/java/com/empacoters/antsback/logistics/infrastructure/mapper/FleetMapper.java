package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.infrastructure.entity.FleetEntity;

public class FleetMapper {
    public static FleetEntity toEntity(Fleet original) {
        if (original == null)
            return null;

        var convertedTrucks = original.listTrucks().stream().map(
            TruckMapper::toEntity
        ).toList();

        return new FleetEntity(
            original.id(),
            original.name(),
            original.codigo(),
            original.placeOfOperation(),
            convertedTrucks
        );
    }

    public static Fleet toDomain(FleetEntity original) {
        if (original == null) { return null; }
        var convertedTrucks = original.getTrucks().stream().map(
            TruckMapper::toDomain
        ).toList();

        return new Fleet(
            original.getId(),
            original.getName(),
            original.getCodigo(),
            original.getPlaceOfOperation(),
            convertedTrucks
        );
    }
}
