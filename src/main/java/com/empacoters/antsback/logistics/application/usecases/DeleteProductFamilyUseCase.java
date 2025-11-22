package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductFamilyUseCase {
    private final ProductFamilyRepository productFamilyRepository;

    public DeleteProductFamilyUseCase(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    public void execute(Long id) {
        var family = productFamilyRepository.findById(id);
        if (family == null) {
            throw new NotFoundException("A família de produtos solicitada não existe");
        }
        productFamilyRepository.delete(id);
    }
}