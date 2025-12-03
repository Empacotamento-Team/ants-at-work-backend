package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackageRequest;
import com.empacoters.antsback.shared.exception.BadRequestException;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreatePackageUseCase {
    private final PackageRepository packageRepository;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;
    private final CreatePackagingUseCase createPackagingUseCase;

    public CreatePackageUseCase(
            PackageRepository packageRepository,
            PackagingRepository packagingRepository,
            ProductRepository productRepository,
            CreatePackagingUseCase createPackagingUseCase) {
        this.packageRepository = packageRepository;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
        this.createPackagingUseCase = createPackagingUseCase;
    }

    public Package execute(Long packagingId, Long productId) {
        if (productId == null) {
            throw new BadRequestException("O ID do produto é obrigatório");
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new NotFoundException("O produto especificado não existe");
        }

        Packaging packaging = null;
        if (packagingId != null) {
            packaging = packagingRepository.findById(packagingId);
            if (packaging == null) {
                throw new NotFoundException("A embalagem especificada não existe");
            }
        }

        Package newPackage = new Package(
                null, null, packaging, product,
                null, null, null, null, null
        );

        return packageRepository.save(newPackage);
    }

    public Package executeWithNewPackaging(CreatePackageRequest request) {
        if (request.getProductId() == null) {
            throw new BadRequestException("O ID do produto é obrigatório");
        }

        Product product = productRepository.findById(request.getProductId());
        if (product == null) {
            throw new NotFoundException("O produto especificado não existe");
        }

        if (request.getPackagingName() == null || request.getPackagingName().trim().isEmpty()) {
            throw new BadRequestException("O nome da embalagem é obrigatório ao criar uma nova embalagem");
        }

        if (request.getPackagingHeight() == null || request.getPackagingWidth() == null || request.getPackagingLength() == null) {
            throw new BadRequestException("As dimensões da embalagem são obrigatórias");
        }

        var dimensions = new Dimensions(
                request.getPackagingHeight(),
                request.getPackagingWidth(),
                request.getPackagingLength()
        );
        Packaging newPackaging = new Packaging(
                null,
                request.getPackagingName(),
                request.getPackagingDescription(),
                dimensions
        );
        Packaging savedPackaging = packagingRepository.save(newPackaging);

        Package newPackage = new Package(
                null, null, savedPackaging, product,
                null, null, null, null, null
        );

        return packageRepository.save(newPackage);
    }
}

