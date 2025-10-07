package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataTruckRepository extends JpaRepository<TruckEntity, Long> {
    List<TruckEntity> findByIdIn(List<Long> ids);

    List<TruckEntity> findByFleetId(Long fleetId);

    @Query(value = "SELECT * FROM trucks t WHERE t.fleet_id = :fleetId LIMIT 5", nativeQuery = true)
    List<TruckEntity> findFiveByFleetId(@Param("fleetId") Long fleetId);

    List<TruckEntity> findByStatus(TruckStatus status);

    List<TruckEntity> findByFleetIdAndStatus(Long fleetId, TruckStatus status);

    Integer countAllByFleetId(Long fleetId);

    Integer countAllByFleetIdAndStatus(Long fleetId, TruckStatus status);
}