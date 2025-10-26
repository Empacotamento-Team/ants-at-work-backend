package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddTrucksToFleetUseCase {
    private final FleetRepository fleetRepository;
    private final TruckRepository truckRepository;

    public AddTrucksToFleetUseCase(FleetRepository fleetRepository, TruckRepository truckRepository) {
        this.fleetRepository = fleetRepository;
        this.truckRepository = truckRepository;
    }

    public void execute(Long fleetId, Long[] trucksIds) {
        var fleet = fleetRepository.findById(fleetId);
        if (fleet == null)
            throw new NotFoundException(
                "Não é possível adicionar caminhões a uma frota que não existe."
            );

        for (var truckId : trucksIds) {
            var truck = truckRepository.byId(truckId);
            fleet.addTruck(truck);
        }

        fleetRepository.save(fleet);
    }
}
