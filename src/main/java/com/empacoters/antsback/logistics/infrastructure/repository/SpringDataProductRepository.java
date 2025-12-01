package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByFamilyId(Long familyId);

    List<ProductEntity> findByBatchEquals(String batch);
    
    @Query("SELECT p FROM products p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:familyId IS NULL OR p.family.id = :familyId) AND " +
           "(:batch IS NULL OR LOWER(p.batch) LIKE LOWER(CONCAT('%', :batch, '%'))) AND " +
           "(:fragile IS NULL OR p.fragile = :fragile)")
    Page<ProductEntity> findAllWithFilters(
        @Param("name") String name,
        @Param("familyId") Long familyId,
        @Param("batch") String batch,
        @Param("fragile") Boolean fragile,
        Pageable pageable
    );
}