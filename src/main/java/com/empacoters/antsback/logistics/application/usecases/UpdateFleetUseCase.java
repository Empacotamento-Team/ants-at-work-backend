package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UpdateFleetUseCase {
    private final FleetRepository fleetRepository;
    private final TruckRepository truckRepository;

    public UpdateFleetUseCase(FleetRepository fleetRepository, TruckRepository truckRepository) {
        this.fleetRepository = fleetRepository;
        this.truckRepository = truckRepository;
    }

    public Fleet execute(Long id, String name, String code, String placeOfOperation, Long[] trucksIds) {
        var newFleet = fleetRepository.findById(id);
        if (newFleet == null) {
            throw new NotFoundException("Não foi possível encontrar a frota solicitada");
        }
        if (name != null) {
            newFleet.changeName(name);
        }
        if (code != null) {
            newFleet.changeCode(code);
        }
        if (placeOfOperation != null) {
            newFleet.changePlaceOfOperation(placeOfOperation);
        }
        if (trucksIds != null) {
            System.out.println("Dayum!");
            var trucks = new ArrayList<>(truckRepository.byIdIn(Arrays.stream(trucksIds).toList()));
            for (var truck : trucks) {
                newFleet.addTruck(truck);
            }
        }

        return this.fleetRepository.save(newFleet);
    }
}
