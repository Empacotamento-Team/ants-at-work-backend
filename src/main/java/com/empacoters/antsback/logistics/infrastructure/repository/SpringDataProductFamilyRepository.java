package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.ProductFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductFamilyRepository extends JpaRepository<ProductFamilyEntity, Long> {
}