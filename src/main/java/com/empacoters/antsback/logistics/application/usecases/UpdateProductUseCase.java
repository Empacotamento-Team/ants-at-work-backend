package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductFamilyRepository productFamilyRepository;

    public UpdateProductUseCase(ProductRepository productRepository, ProductFamilyRepository productFamilyRepository) {
        this.productRepository = productRepository;
        this.productFamilyRepository = productFamilyRepository;
    }

    public Product execute(Long id, String name, Long familyId, Dimensions dimensions, Double weight, Double maxSupportedWeight, String batch, boolean fragile) {
        var product = productRepository.findById(id);
        if (product == null) {
            throw new NotFoundException("Produto não encontrado");
        }

        if (name != null) {
            product.changeName(name);
        }

        if (familyId != null) {
            var family = productFamilyRepository.findById(familyId);
            if (family == null) {
                throw new BadRequestException("A família especificada não existe");
            }
            product.changeFamily(family);
        }

        if (dimensions != null) {
            product.changeDimensions(dimensions);
        }

        if (weight != null) {
            product.changeWeight(weight);
        }

        if (maxSupportedWeight != null) {
            product.changeMaxSupportedWeight(maxSupportedWeight);
        }

        if (batch != null) {
            product.changeBatch(batch);
        }

        product.changeFragile(fragile);

        return productRepository.save(product);
    }
}

