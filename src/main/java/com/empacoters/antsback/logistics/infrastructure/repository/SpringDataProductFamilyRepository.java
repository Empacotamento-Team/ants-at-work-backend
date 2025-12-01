package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.ProductFamilyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataProductFamilyRepository extends JpaRepository<ProductFamilyEntity, Long> {
    @Query("SELECT f FROM product_families f WHERE " +
           "(:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:description IS NULL OR LOWER(f.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<ProductFamilyEntity> findAllWithFilters(
        @Param("name") String name,
        @Param("description") String description,
        Pageable pageable
    );
}