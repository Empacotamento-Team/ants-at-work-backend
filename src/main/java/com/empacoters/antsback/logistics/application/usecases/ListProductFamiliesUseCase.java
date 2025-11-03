package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListProductFamiliesUseCase {
    private final ProductFamilyRepository productFamilyRepository;

    public ListProductFamiliesUseCase(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    public List<ProductFamily> execute() {
        return productFamilyRepository.findAll();
    }
}