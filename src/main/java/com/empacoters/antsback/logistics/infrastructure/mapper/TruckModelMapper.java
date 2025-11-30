package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckModelEntity;

public class TruckModelMapper {
    public static TruckModel toDomain(TruckModelEntity entity) {
        if (entity == null)
            return null;

        return new TruckModel(
            entity.getId(), entity.getName(),
            entity.getDescription(), entity.getDefaultMaximumCapacity(),
            entity.getDefaultInternalDimensions(), entity.getDefaultTruckType()
        );
    }

    public static TruckModelEntity toEntity(TruckModel model) {
        if (model == null)
            return null;

        var entity = new TruckModelEntity();

        entity.setId(model.id());
        entity.setName(model.name());
        entity.setDescription(model.description());
        entity.setDefaultMaximumCapacity(model.defaultMaximumCapacity());
        entity.setDefaultInternalDimensions(model.defaultInternalDimensions());
        entity.setDefaultTruckType(model.defaultTruckType());

        return entity;
    }
}
