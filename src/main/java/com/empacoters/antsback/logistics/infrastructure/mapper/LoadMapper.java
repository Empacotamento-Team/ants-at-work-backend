package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.infrastructure.entity.LoadEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.ShipmentEntity;

public class LoadMapper {
    public static LoadEntity toEntity(Load load) {
        ShipmentEntity shipmentEntity = null;
        if (load.shipmentId() != null) {
            shipmentEntity = new ShipmentEntity();
            shipmentEntity.setId(load.shipmentId());
        }

        var loadEntity = new LoadEntity();

        var packageEntites = load.packages().stream().map(pkg -> {
            var entity = PackageMapper.toEntity(pkg);
            entity.setLoad(loadEntity);
            return entity;
        }).toList();
        loadEntity.setId(load.id());
        loadEntity.setRemainingWeight(load.remainingWeight());
        loadEntity.setTotalAllocatedWeight(load.totalAllocatedWeight());
        loadEntity.setTotalAllocatedVolume(load.totalAllocatedVolume());
        loadEntity.setVolumeOccupationPercentage(load.volumeOccupationPercentage());
        loadEntity.setXPosition(load.xPosition());
        loadEntity.setYPosition(load.yPosition());
        loadEntity.setZPosition(load.zPosition());

        loadEntity.setRelatedTruck(TruckMapper.toEntity(load.relatedTruck()));
        loadEntity.setShipment(shipmentEntity);
        loadEntity.setPackages(packageEntites);

        return loadEntity;
    }

    public static Load toDomain(LoadEntity loadEntity) {
        var packages = loadEntity.getPackages().stream().map(PackageMapper::toDomain).toList();

        Long shipmentId = null;
        if (loadEntity.getShipment() != null) {
            shipmentId = loadEntity.getShipment().getId();
        }

        return new Load(
            loadEntity.getId(),
            shipmentId,
            TruckMapper.toDomain(loadEntity.getRelatedTruck()),
            packages,
            loadEntity.getTotalAllocatedWeight(),
            loadEntity.getRemainingWeight(),
            loadEntity.getTotalAllocatedVolume(),
            loadEntity.getVolumeOccupationPercentage(),
            loadEntity.getXPosition(),
            loadEntity.getYPosition(),
            loadEntity.getZPosition()
        );
    }
}
