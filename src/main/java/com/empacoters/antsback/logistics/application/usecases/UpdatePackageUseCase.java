package com.empacoters.antsback.logistics.application.usecases;

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
public class UpdatePackageUseCase {
    private final PackageRepository packageRepository;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;

    public UpdatePackageUseCase(PackageRepository packageRepository, PackagingRepository packagingRepository, ProductRepository productRepository) {
        this.packageRepository = packageRepository;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
    }

    public Package execute(Long id, Long packagingId, Long productId, Double supportedWeight) {
        var pkg = packageRepository.findById(id);
        if (pkg == null) {
            throw new NotFoundException("Package não encontrado com o ID: " + id);
        }

        // Validar e atualizar packaging
        if (packagingId != null) {
            var packaging = packagingRepository.findById(packagingId);
            if (packaging == null) {
                throw new BadRequestException("A embalagem especificada não existe");
            }
            pkg.changePackaging(packaging);
        }

        // Validar e atualizar produto
        if (productId != null) {
            Product product = productRepository.findById(productId);
            if (product == null) {
                throw new BadRequestException("O produto especificado não foi encontrado");
            }
            pkg.changeProduct(product);
        }

        // Atualizar peso suportado
        if (supportedWeight != null) {
            pkg.changeSupportedWeight(supportedWeight);
        }

        return packageRepository.save(pkg);
    }
}
