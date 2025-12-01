package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFamilyRepository {
    List<ProductFamily> findAll();
    Page<ProductFamily> findAll(Pageable pageable);
    Page<ProductFamily> findAllWithFilters(String name, String description, Pageable pageable);
    ProductFamily findById(Long id);
    ProductFamily save(ProductFamily family);
    void delete(Long id);
}
