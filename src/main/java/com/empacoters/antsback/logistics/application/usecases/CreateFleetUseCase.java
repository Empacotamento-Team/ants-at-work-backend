package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
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

    public Fleet execute(String fleetName, String fleetCode, String placeOfOperation, Long[] trucksIds) {
        if (fleetRepository.findByCode(fleetCode) != null) {
            throw new BadRequestException("Uma frota de código \"" + fleetCode + "\" já existe");
        }

        List<Truck> trucks = trucksIds != null ? Arrays.stream(trucksIds).map(truckRepository::byId).toList() : null;
        Fleet newFleet = new Fleet(null, fleetName, fleetCode, placeOfOperation, trucks);

        return fleetRepository.save(newFleet);
    }
}
