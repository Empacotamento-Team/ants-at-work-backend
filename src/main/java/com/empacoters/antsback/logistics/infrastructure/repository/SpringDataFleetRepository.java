package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.FleetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataFleetRepository extends JpaRepository<FleetEntity, Long> {
    Optional<FleetEntity> findByCodigo(String codigo);
    
    @Query("SELECT DISTINCT f FROM fleets f LEFT JOIN f.trucks t WHERE " +
           "(:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:truckPlate IS NULL OR LOWER(t.plate) LIKE LOWER(CONCAT('%', :truckPlate, '%')))")
    Page<FleetEntity> findAllWithFilters(
        @Param("name") String name,
        @Param("truckPlate") String truckPlate,
        Pageable pageable
    );
}
