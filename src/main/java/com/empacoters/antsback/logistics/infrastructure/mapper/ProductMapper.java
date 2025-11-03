package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.infrastructure.entity.ProductEntity;

public class ProductMapper {
    public static ProductEntity toEntity(Product product) {
        if (product == null)
            return null;

        return new ProductEntity(
            product.id(),
            product.name(),
            ProductFamilyMapper.toEntity(product.family()),
            product.dimensions(),
            product.weight(),
            product.fragile(),
            product.maxSupportedWeight(),
            product.batch()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null)
            return null;

        return new Product(
            entity.getId(),
            entity.getName(),
            ProductFamilyMapper.toDomain(entity.getFamily()),
            entity.getDimensions(),
            entity.getWeight(),
            entity.getMaxSupportedWeight(),
            entity.getBatch(),
            entity.isFragile()
        );
    }
}