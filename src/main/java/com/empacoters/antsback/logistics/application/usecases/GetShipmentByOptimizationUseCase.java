package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.domain.repository.OptimizationRepository;
import com.empacoters.antsback.logistics.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GetShipmentByOptimizationUseCase {
    private final OptimizationRepository optimizationRepository;
    private final ShipmentRepository shipmentRepository;

    public GetShipmentByOptimizationUseCase(
            OptimizationRepository optimizationRepository,
            ShipmentRepository shipmentRepository) {
        this.optimizationRepository = optimizationRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment execute(Long optimizationId) {
        Optimization optimization = optimizationRepository.findById(optimizationId);
        if (optimization == null) {
            return null;
        }

        // Buscar shipments criados após a optimization (com uma margem de 5 segundos antes)
        Instant optimizationDate = optimization.createdAt();
        Instant startDate = optimizationDate.minus(5, ChronoUnit.SECONDS);
        Instant endDate = optimizationDate.plus(30, ChronoUnit.SECONDS);

        List<Shipment> shipments = shipmentRepository.findByDateRange(startDate, endDate);
        
        // Retornar o shipment mais recente
        if (shipments.isEmpty()) {
            return null;
        }

        return shipments.stream()
                .max((s1, s2) -> s1.createdAt().compareTo(s2.createdAt()))
                .orElse(null);
    }
}

