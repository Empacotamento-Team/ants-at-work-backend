package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.infrastructure.entity.ProductFamilyEntity;

public class ProductFamilyMapper {
    public static ProductFamilyEntity toEntity(ProductFamily family) {
        if (family == null)
            return null;

        return new ProductFamilyEntity(
            family.id(),
            family.name(),
            family.defaultMaxSupportedWeight(),
            null,
            family.description()
        );
    }

    public static ProductFamily toDomain(ProductFamilyEntity entity) {
        if (entity == null)
            return null;

        return new ProductFamily(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getDefaultMaxSupportedWeight()
        );
    }
}