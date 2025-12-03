package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.GetShipmentByOptimizationUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetShipmentUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListShipmentsUseCase;
import com.empacoters.antsback.logistics.domain.model.Shipment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    private final GetShipmentUseCase getShipmentUseCase;
    private final ListShipmentsUseCase listShipmentsUseCase;
    private final GetShipmentByOptimizationUseCase getShipmentByOptimizationUseCase;

    public ShipmentController(
            GetShipmentUseCase getShipmentUseCase,
            ListShipmentsUseCase listShipmentsUseCase,
            GetShipmentByOptimizationUseCase getShipmentByOptimizationUseCase) {
        this.getShipmentUseCase = getShipmentUseCase;
        this.listShipmentsUseCase = listShipmentsUseCase;
        this.getShipmentByOptimizationUseCase = getShipmentByOptimizationUseCase;
    }

    @GetMapping
    public List<Shipment> list() {
        return listShipmentsUseCase.execute();
    }

    @GetMapping("/{id}")
    public Shipment getById(@PathVariable Long id) {
        return getShipmentUseCase.execute(id);
    }

    @GetMapping("/by-date-range")
    public List<Shipment> getByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Instant startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Instant endDate) {
        return listShipmentsUseCase.byDateRange(startDate, endDate);
    }

    @GetMapping("/by-optimization/{optimizationId}")
    public ResponseEntity<Shipment> getByOptimizationId(@PathVariable Long optimizationId) {
        Shipment shipment = getShipmentByOptimizationUseCase.execute(optimizationId);
        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipment);
    }
}