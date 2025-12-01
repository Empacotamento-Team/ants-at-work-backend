package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListProductsUseCase {
    private final ProductRepository productRepository;

    public ListProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.findAll();
    }

    public Page<Product> execute(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Page<Product> execute(String name, Long familyId, String batch, Boolean fragile, Pageable pageable) {
        return productRepository.findAllWithFilters(name, familyId, batch, fragile, pageable);
    }

    public List<Product> byFamilyId(Long familyId) {
        return productRepository.findByFamilyId(familyId);
    }
}