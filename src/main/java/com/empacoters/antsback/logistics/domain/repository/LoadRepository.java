package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Load;

import java.util.List;

public interface LoadRepository {
    List<Load> findAll();
    List<Load> findByShipmentId(Long shipmentId);
    List<Load> findByTruckId(Long truckId);
    Load findById(Long id);
    Load save(Load load);
    void delete(Long loadId);
}
