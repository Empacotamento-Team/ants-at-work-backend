package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ListShipmentsUseCase {
    private final ShipmentRepository shipmentRepository;

    public ListShipmentsUseCase(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public List<Shipment> execute() {
        return shipmentRepository.findAll();
    }

    public List<Shipment> byDateRange(Instant startDate, Instant endDate) {
        return shipmentRepository.findByDateRange(startDate, endDate);
    }
}