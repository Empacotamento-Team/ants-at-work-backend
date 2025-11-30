package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.infrastructure.entity.LoadEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.ShipmentEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShipmentMapper {
    public static Shipment toDomain(ShipmentEntity shipmentEntity) {
        return new Shipment(
                shipmentEntity.getId(),
                shipmentEntity.getLoads().stream()
                        .map(LoadMapper::toDomain)
                        .collect(Collectors.toList()),
                shipmentEntity.getCreatedAt()
        );
    }

    public static ShipmentEntity toEntity(Shipment shipment) {
        ShipmentEntity shipmentEntity = new ShipmentEntity();
        shipmentEntity.setId(shipment.id());
        shipmentEntity.setLoads(new ArrayList<>());
        shipmentEntity.setCreatedAt(shipment.createdAt());

        for (Load domainLoad : shipment.loads()) {
            domainLoad.changeShipmentId(shipmentEntity.getId());
            LoadEntity loadEntity = LoadMapper.toEntity(domainLoad);
            shipmentEntity.getLoads().add(loadEntity);
        }

        return shipmentEntity;
    }
}
