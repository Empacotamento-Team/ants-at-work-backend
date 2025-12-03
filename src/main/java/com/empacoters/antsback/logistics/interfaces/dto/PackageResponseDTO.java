package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.model.Product;

public record PackageResponseDTO(
        Long id,
        Long loadId,
        PackagingInfo packaging,
        ProductInfo product,
        Double supportedWeight,
        Double xPosition,
        Double yPosition,
        Double zPosition,
        String orientation
) {
    public static PackageResponseDTO fromPackage(Package pkg) {
        if (pkg == null)
            return null;

        PackagingInfo packagingInfo = null;
        if (pkg.packaging() != null) {
            var dims = pkg.packaging().internalDimensions();
            packagingInfo = new PackagingInfo(
                    pkg.packaging().id(),
                    pkg.packaging().name(),
                    pkg.packaging().description(),
                    dims != null ? dims.height() : null,
                    dims != null ? dims.width() : null,
                    dims != null ? dims.length() : null
            );
        }

        ProductInfo productInfo = null;
        if (pkg.product() != null) {
            productInfo = new ProductInfo(
                    pkg.product().id(),
                    pkg.product().name()
            );
        }

        return new PackageResponseDTO(
                pkg.id(),
                pkg.loadId(),
                packagingInfo,
                productInfo,
                pkg.supportedWeight(),
                pkg.xPosition(),
                pkg.yPosition(),
                pkg.zPosition(),
                pkg.orientation()
        );
    }

    public record PackagingInfo(
            Long id,
            String name,
            String description,
            Double height,
            Double width,
            Double length
    ) {}

    public record ProductInfo(
            Long id,
            String name
    ) {}
}

