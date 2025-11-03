package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.domain.repository.LoadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListLoadsUseCase {
    private final LoadRepository loadRepository;

    public ListLoadsUseCase(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    public List<Load> execute() {
        return loadRepository.findAll();
    }

    public List<Load> byShipmentId(Long shipmentId) {
        return loadRepository.findByShipmentId(shipmentId);
    }

    public List<Load> byTruckId(Long truckId) {
        return loadRepository.findByTruckId(truckId);
    }
}