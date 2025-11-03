package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductFamilyRepository productFamilyRepository;

    public CreateProductUseCase(ProductRepository productRepository, ProductFamilyRepository productFamilyRepository) {
        this.productRepository = productRepository;
        this.productFamilyRepository = productFamilyRepository;
    }

    public Product execute(Product product) {
        if (product.family() == null || product.family().id() == null) {
            throw new BadRequestException("A família do produto é obrigatória");
        }

        var family = productFamilyRepository.findById(product.family().id());
        if (family == null) {
            throw new BadRequestException("A família especificada não existe");
        }

        return productRepository.save(product);
    }
}