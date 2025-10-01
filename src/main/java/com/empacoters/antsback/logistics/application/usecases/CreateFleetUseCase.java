package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CreateFleetUseCase {
    private final TruckRepository truckRepository;
    private final FleetRepository fleetRepository;

    public CreateFleetUseCase(TruckRepository truckRepository, FleetRepository fleetRepository) {
        this.truckRepository = truckRepository;
        this.fleetRepository = fleetRepository;
    }

    public void execute(String fleetName, String fleetCode, String placeOfOperation, Long[] trucksIds) {
        List<Truck> trucks = Arrays.stream(trucksIds).map(truckRepository::byId).toList();
        Fleet newFleet = new Fleet(null, fleetName, fleetCode, placeOfOperation, trucks);

        fleetRepository.save(newFleet);
    }
}
