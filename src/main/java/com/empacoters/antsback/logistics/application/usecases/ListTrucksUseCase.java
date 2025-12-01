package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTrucksUseCase {

    private final TruckRepository truckRepository;

    public ListTrucksUseCase(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }
    
    public List<Truck> execute(Long fleetId, TruckStatus status)
    {
        return truckRepository.byFleetIdAndStatus(fleetId, status);
    }
    
    public Page<Truck> execute(Long fleetId, TruckStatus status, Pageable pageable)
    {
        return truckRepository.byFleetIdAndStatus(fleetId, status, pageable);
    }
    
    public Page<Truck> execute(String plate, TruckType type, TruckStatus status, Long modelId, Pageable pageable)
    {
        return truckRepository.findAllWithFilters(plate, type, status, modelId, pageable);
    }

}
