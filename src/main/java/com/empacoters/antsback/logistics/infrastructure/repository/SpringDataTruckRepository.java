package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.infrastructure.entity.TruckEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataTruckRepository extends JpaRepository<TruckEntity, Long> {
    @EntityGraph(attributePaths = {"model"})
    @Override
    List<TruckEntity> findAll();

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findByFleetId(Long fleetId);

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findTop5ByFleetId(Long fleetId);

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findByStatus(TruckStatus status);

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findByFleetIdAndStatus(Long fleetId, TruckStatus status);

    @EntityGraph(attributePaths = {"model"})
    Page<TruckEntity> findByFleetIdAndStatus(Long fleetId, TruckStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"model"})
    Page<TruckEntity> findByFleetId(Long fleetId, Pageable pageable);

    @EntityGraph(attributePaths = {"model"})
    Page<TruckEntity> findByStatus(TruckStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"model"})
    @Override
    Page<TruckEntity> findAll(Pageable pageable);

    Integer countAllByFleetId(Long fleetId);

    Integer countAllByFleetIdAndStatus(Long fleetId, TruckStatus status);

    @EntityGraph(attributePaths = {"model"})
    List<TruckEntity> findByPlate(String plate);

    @EntityGraph(attributePaths = {"model"})
    @Override
    Optional<TruckEntity> findById(Long id);
    
    @EntityGraph(attributePaths = {"model"})
    @Query("SELECT t FROM trucks t WHERE " +
           "(:plate IS NULL OR LOWER(t.plate) LIKE LOWER(CONCAT('%', :plate, '%'))) AND " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:modelId IS NULL OR t.model.id = :modelId)")
    Page<TruckEntity> findAllWithFilters(
        @Param("plate") String plate,
        @Param("type") TruckType type,
        @Param("status") TruckStatus status,
        @Param("modelId") Long modelId,
        Pageable pageable
    );
}