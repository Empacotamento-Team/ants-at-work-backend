package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataPackageRepository extends JpaRepository<PackageEntity, Long> {
    List<PackageEntity> findByLoadId(Long loadId);
}