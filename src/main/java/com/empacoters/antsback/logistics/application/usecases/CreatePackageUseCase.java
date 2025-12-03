package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreatePackageUseCase {
    private final PackageRepository packageRepository;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;

    public CreatePackageUseCase(PackageRepository packageRepository, PackagingRepository packagingRepository, ProductRepository productRepository) {
        this.packageRepository = packageRepository;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
    }

    public Package execute(Long packagingId, Long productId, Double supportedWeight) {
        Packaging packaging = null;

        // Se packagingId foi fornecido, busca a embalagem existente
        if (packagingId != null) {
            packaging = packagingRepository.findById(packagingId);
            if (packaging == null) {
                throw new NotFoundException("A embalagem especificada não existe");
            }
        }

        // Validar e buscar produto
        if (productId == null) {
            throw new BadRequestException("Um produto deve ser associado ao package");
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new BadRequestException("O produto especificado não foi encontrado");
        }

        var newPackage = new Package(null, null, packaging, product, supportedWeight, null, null, null, null);
        return packageRepository.save(newPackage);
    }

    public Package executeWithNewPackaging(String packagingName, String packagingDescription, 
                                          Double height, Double width, Double length,
                                          Long productId, Double supportedWeight) {
        // Criar nova embalagem
        var dimensions = new Dimensions(height, width, length);
        var newPackaging = new Packaging(null, packagingName, packagingDescription, dimensions);
        var savedPackaging = packagingRepository.save(newPackaging);

        // Validar e buscar produto
        if (productId == null) {
            throw new BadRequestException("Um produto deve ser associado ao package");
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new BadRequestException("O produto especificado não foi encontrado");
        }

        var newPackage = new Package(null, null, savedPackaging, product, supportedWeight, null, null, null, null);
        return packageRepository.save(newPackage);
    }
}
