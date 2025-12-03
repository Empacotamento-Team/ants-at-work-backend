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
public class UpdatePackageUseCase {
    private final PackageRepository packageRepository;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;

    public UpdatePackageUseCase(
            PackageRepository packageRepository,
            PackagingRepository packagingRepository,
            ProductRepository productRepository) {
        this.packageRepository = packageRepository;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
    }

    public Package execute(Long id, CreatePackageRequest request) {
        Package existingPackage = packageRepository.findById(id);
        if (existingPackage == null) {
            throw new NotFoundException("O pacote solicitado não existe");
        }

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId());
            if (product == null) {
                throw new NotFoundException("O produto especificado não existe");
            }
            existingPackage.changeProduct(product);
        }

        // Se tem packagingName, cria nova embalagem
        if (request.getPackagingName() != null && !request.getPackagingName().trim().isEmpty()) {
            if (request.getPackagingHeight() == null || request.getPackagingWidth() == null || request.getPackagingLength() == null) {
                throw new BadRequestException("As dimensões da embalagem são obrigatórias ao criar uma nova embalagem");
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
            existingPackage.changePackaging(savedPackaging);
        } else if (request.getPackagingId() != null) {
            // Se não tem packagingName mas tem packagingId, usa embalagem existente
            Packaging packaging = packagingRepository.findById(request.getPackagingId());
            if (packaging == null) {
                throw new NotFoundException("A embalagem especificada não existe");
            }
            existingPackage.changePackaging(packaging);
        }

        return packageRepository.save(existingPackage);
    }
}

