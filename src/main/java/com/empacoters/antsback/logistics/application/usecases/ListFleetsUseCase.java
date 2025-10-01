package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.interfaces.dto.FleetResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ListFleetsUseCase {
    private final TruckRepository truckRepository;
    private final FleetRepository fleetRepository;

    public ListFleetsUseCase(TruckRepository truckRepository, FleetRepository fleetRepository) {
        this.truckRepository = truckRepository;
        this.fleetRepository = fleetRepository;
    }

    public void execute() {
//        var fleetsDtos = fleetRepository.listAll().stream().map(
//            fleet -> new FleetResponseDTO(fleet.name(), fleet.codigo(), fleet.)
//        );
//        for (var fleet : fleets) {
//            var trucks =
//            fleet.changeListOfTrucks();
//        }
//        truckRepository.fiveByFleetId();
    }
}