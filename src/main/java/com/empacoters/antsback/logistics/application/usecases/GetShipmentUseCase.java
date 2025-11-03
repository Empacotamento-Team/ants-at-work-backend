package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.domain.repository.ShipmentRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetShipmentUseCase {
    private final ShipmentRepository shipmentRepository;

    public GetShipmentUseCase(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment execute(Long id) {
        var shipment = shipmentRepository.findById(id);
        if (shipment == null) {
            throw new NotFoundException("O carregamento solicitado não existe");
        }
        return shipment;
    }
}