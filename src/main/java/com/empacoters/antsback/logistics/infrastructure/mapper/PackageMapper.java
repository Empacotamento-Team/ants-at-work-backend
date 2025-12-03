package com.empacoters.antsback.logistics.infrastructure.mapper;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.infrastructure.entity.LoadEntity;
import com.empacoters.antsback.logistics.infrastructure.entity.PackageEntity;

public class PackageMapper {
    public static Package toDomain(PackageEntity packageEntity) {
        if (packageEntity == null)
            return null;

        var packageLoad = packageEntity.getLoad();

        return new Package(
            packageEntity.getId(),
            packageLoad != null ? packageLoad.getId() : null,
            PackagingMapper.toDomain(packageEntity.getPackaging()),
            ProductMapper.toDomain(packageEntity.getProduct()),
            packageEntity.getSupportedWeight(),
            packageEntity.getXPosition(),
            packageEntity.getYPosition(),
            packageEntity.getZPosition(),
            packageEntity.getOrientation()
        );
    }

    public static PackageEntity toEntity(Package pkg) {
        if (pkg == null)
            return null;

        var pkgEntity = new PackageEntity();
        pkgEntity.setId(pkg.id());
        pkgEntity.setPackaging(PackagingMapper.toEntity(pkg.packaging()));
        
        // Só associa Load se loadId não for null
        if (pkg.loadId() != null) {
            var load = new LoadEntity();
            load.setId(pkg.loadId());
            pkgEntity.setLoad(load);
        } else {
            pkgEntity.setLoad(null);
        }
        
        pkgEntity.setProduct(ProductMapper.toEntity(pkg.product()));
        pkgEntity.setSupportedWeight(pkg.supportedWeight());
        pkgEntity.setXPosition(pkg.xPosition());
        pkgEntity.setYPosition(pkg.yPosition());
        pkgEntity.setZPosition(pkg.zPosition());
        pkgEntity.setOrientation(pkg.orientation());

        return pkgEntity;
    }
}
