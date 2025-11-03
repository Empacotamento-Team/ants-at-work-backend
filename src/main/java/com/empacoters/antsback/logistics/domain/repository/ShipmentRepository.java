package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Shipment;

import java.util.Date;
import java.util.List;

public interface ShipmentRepository {
    List<Shipment> findAll();
    List<Shipment> findByDateRange(Date from, Date to);
    Shipment findById(Long id);
    Shipment save(Shipment shipment);
    void delete(Long shipmentId);
}
