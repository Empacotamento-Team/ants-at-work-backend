package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.PackagingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPackagingRepository extends JpaRepository<PackagingEntity, Long> {
}