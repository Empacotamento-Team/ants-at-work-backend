package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductFamily> execute(Pageable pageable) {
        return productFamilyRepository.findAll(pageable);
    }
    
    public Page<ProductFamily> execute(String name, String description, Pageable pageable) {
        return productFamilyRepository.findAllWithFilters(name, description, pageable);
    }
}