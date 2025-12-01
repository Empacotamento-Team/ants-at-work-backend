package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductFamilyUseCase {
    private final ProductFamilyRepository productFamilyRepository;

    public UpdateProductFamilyUseCase(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    public ProductFamily execute(Long id, String name, String description, Double defaultMaxSupportedWeight) {
        var family = productFamilyRepository.findById(id);
        if (family == null) {
            throw new NotFoundException("Família de produto não encontrada");
        }

        if (name != null) {
            family.changeName(name);
        }

        if (description != null) {
            family.changeDescription(description);
        }

        if (defaultMaxSupportedWeight != null) {
            family.changeDefaultMaxSupportedWeight(defaultMaxSupportedWeight);
        }

        return productFamilyRepository.save(family);
    }
}

