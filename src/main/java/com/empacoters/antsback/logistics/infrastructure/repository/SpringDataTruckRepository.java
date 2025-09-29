package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTruckRepository extends JpaRepository<TruckEntity, Long> {
    List<TruckEntity> findByFleetId(Long fleetId);

    List<TruckEntity> findByStatus(TruckStatus status);

    List<TruckEntity> findByFleetIdAndStatus(Long fleetId, TruckStatus status);
}