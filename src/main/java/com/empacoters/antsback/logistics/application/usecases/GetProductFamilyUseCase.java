package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProductFamilyUseCase {
    private final ProductFamilyRepository productFamilyRepository;

    public GetProductFamilyUseCase(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    public ProductFamily execute(Long id) {
        var family = productFamilyRepository.findById(id);
        if (family == null) {
            throw new NotFoundException("A família de produtos solicitada não existe");
        }
        return family;
    }
}