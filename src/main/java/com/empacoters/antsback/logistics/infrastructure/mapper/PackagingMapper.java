package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.infrastructure.entity.PackagingEntity;

public class PackagingMapper {
    public static PackagingEntity toEntity(Packaging packaging) {
        if (packaging == null)
            return null;

        return new PackagingEntity(
            packaging.id(),
            packaging.name(),
            packaging.description(),
            packaging.internalDimensions()
        );
    }

    public static Packaging toDomain(PackagingEntity entity) {
        if (entity == null)
            return null;

        return new Packaging(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getInternalDimensions()
        );
    }
}