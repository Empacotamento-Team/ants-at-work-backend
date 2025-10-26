package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
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

}
