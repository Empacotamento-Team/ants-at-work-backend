package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllWithFilters(String name, Long familyId, String batch, Boolean fragile, Pageable pageable);
    List<Product> findByBatch(String batch);
    List<Product> findByFamilyId(Long familyId);
    Product findById(Long id);
    Product save(Product product);
    void delete(Long productId);
}
