package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;

import java.util.List;

public interface ProductFamilyRepository {
    List<ProductFamily> findAll();
    ProductFamily findById(Long id);
    ProductFamily save(ProductFamily family);
    void delete(Long id);
}
