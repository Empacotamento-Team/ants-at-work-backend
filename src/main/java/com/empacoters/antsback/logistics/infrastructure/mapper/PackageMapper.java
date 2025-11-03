package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.infrastructure.entity.PackageEntity;

import java.util.stream.Collectors;

public class PackageMapper {
    public static Package toDomain(PackageEntity packageEntity) {
        return new Package(
            packageEntity.getId(),
            PackagingMapper.toDomain(packageEntity.getPackaging()),
            packageEntity.getProducts().stream().map(ProductMapper::toDomain).collect(Collectors.toList()),
            packageEntity.getSupportedWeight()
        );
    }

    public static PackageEntity toEntity(Package packageEntity) {
        return new PackageEntity(
            packageEntity.id(),
            null,
            null,
            null,
            packageEntity.supportedWeight()
        );
    }
}
