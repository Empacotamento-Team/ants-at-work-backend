package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.infrastructure.entity.LoadEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.ShipmentEntity;

public class LoadMapper {
    public static LoadEntity toEntity(Load load) {
        var packageEntites = load.packages().stream().map(PackageMapper::toEntity).toList();

        return new LoadEntity(
            load.id(),
            TruckMapper.toEntity(load.relatedTruck()),
            new ShipmentEntity(load.shipmentId(), null, null),
            packageEntites
        );
    }

    public static Load toDomain(LoadEntity loadEntity) {
        var packages = loadEntity.getPackages().stream().map(PackageMapper::toDomain).toList();

        return new Load(
            loadEntity.getId(),
            loadEntity.getShipment().getId(),
            TruckMapper.toDomain(loadEntity.getRelatedTruck()),
            packages
        );
    }
}
