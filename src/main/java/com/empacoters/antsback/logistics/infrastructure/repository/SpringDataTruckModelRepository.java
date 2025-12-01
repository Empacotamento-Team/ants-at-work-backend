package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataTruckModelRepository extends JpaRepository<TruckModelEntity, Long> {
    @Query("SELECT t FROM truck_models t WHERE " +
           "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:type IS NULL OR t.defaultTruckType = :type)")
    Page<TruckModelEntity> findAllWithFilters(
        @Param("name") String name,
        @Param("type") TruckType type,
        Pageable pageable
    );
}
