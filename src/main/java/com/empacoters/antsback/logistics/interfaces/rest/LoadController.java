package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.GetLoadUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListLoadsUseCase;
import com.empacoters.antsback.logistics.domain.model.Load;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loads")
public class LoadController {
    private final GetLoadUseCase getLoadUseCase;
    private final ListLoadsUseCase listLoadsUseCase;

    public LoadController(GetLoadUseCase getLoadUseCase, ListLoadsUseCase listLoadsUseCase) {
        this.getLoadUseCase = getLoadUseCase;
        this.listLoadsUseCase = listLoadsUseCase;
    }

    @GetMapping
    public List<Load> list() {
        return listLoadsUseCase.execute();
    }

    @GetMapping("/{id}")
    public Load getById(@PathVariable Long id) {
        return getLoadUseCase.execute(id);
    }

    @GetMapping("/by-shipment/{shipmentId}")
    public List<Load> getByShipmentId(@PathVariable Long shipmentId) {
        return listLoadsUseCase.byShipmentId(shipmentId);
    }

    @GetMapping("/by-truck/{truckId}")
    public List<Load> getByTruckId(@PathVariable Long truckId) {
        return listLoadsUseCase.byTruckId(truckId);
    }
}