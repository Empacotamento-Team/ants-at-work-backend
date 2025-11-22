package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> findByBatch(String batch);
    List<Product> findByFamilyId(Long familyId);
    Product findById(Long id);
    Product save(Product product);
    void delete(Long productId);
}
