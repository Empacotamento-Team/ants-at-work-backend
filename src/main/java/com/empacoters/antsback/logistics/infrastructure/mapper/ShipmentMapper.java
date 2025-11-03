package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.infrastructure.entity.ShipmentEntity;

import java.util.stream.Collectors;

public class ShipmentMapper {
    public static Shipment toDomain(ShipmentEntity shipmentEntity) {
        return new Shipment(
                shipmentEntity.getId(),
                shipmentEntity.getLoads().stream()
                        .map(LoadMapper::toDomain)
                        .collect(Collectors.toList()),
                shipmentEntity.getDate()
        );
    }

    public static ShipmentEntity toEntity(Shipment shipment) {
        return new ShipmentEntity(
                shipment.id(),
                shipment.loads().stream()
                        .map(LoadMapper::toEntity)
                        .collect(Collectors.toList()),
                shipment.date()
        );
    }
}
