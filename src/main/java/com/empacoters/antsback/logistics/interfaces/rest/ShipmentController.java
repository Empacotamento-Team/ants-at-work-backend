package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.GetShipmentUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListShipmentsUseCase;
import com.empacoters.antsback.logistics.domain.model.Shipment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    private final GetShipmentUseCase getShipmentUseCase;
    private final ListShipmentsUseCase listShipmentsUseCase;

    public ShipmentController(
            GetShipmentUseCase getShipmentUseCase,
            ListShipmentsUseCase listShipmentsUseCase) {
        this.getShipmentUseCase = getShipmentUseCase;
        this.listShipmentsUseCase = listShipmentsUseCase;
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
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return listShipmentsUseCase.byDateRange(startDate, endDate);
    }
}