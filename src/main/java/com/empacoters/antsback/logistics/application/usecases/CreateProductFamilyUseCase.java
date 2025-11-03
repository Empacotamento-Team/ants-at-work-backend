package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductFamilyUseCase {
    private final ProductFamilyRepository productFamilyRepository;

    public CreateProductFamilyUseCase(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    public ProductFamily execute(ProductFamily productFamily) {
        return productFamilyRepository.save(productFamily);
    }
}