package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.LoadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataLoadRepository extends JpaRepository<LoadEntity, Long> {
    List<LoadEntity> findByShipmentId(Long shipmentId);
    List<LoadEntity> findByRelatedTruckId(Long truckId);
}