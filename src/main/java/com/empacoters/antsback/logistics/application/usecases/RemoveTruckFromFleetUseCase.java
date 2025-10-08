package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class RemoveTruckFromFleetUseCase {
    private final FleetRepository fleetRepository;
    private final TruckRepository truckRepository;

    public RemoveTruckFromFleetUseCase(FleetRepository fleetRepository, TruckRepository truckRepository) {
        this.fleetRepository = fleetRepository;
        this.truckRepository = truckRepository;
    }

    public void execute(Long fleetId, Long[] trucksIds) {
        var fleet = fleetRepository.findById(fleetId);

        if (trucksIds == null || trucksIds.length == 0) {
            throw new NotFoundException("Não é possível executar sem antes especificar os caminhões a serem removidos.");
        }

        if (fleet == null) {
            throw new NotFoundException("Não foi possível encontrar a frota desejada");
        }

        for (var truck : fleet.listTrucks()) {
            if (Arrays.stream(trucksIds).anyMatch(id -> Objects.equals(id, truck.id()))) {
                var truckBd = truckRepository.byId(truck.id());
                truckBd.changeFleet(null);
                truckRepository.save(truckBd);
            }
        }
    }
}
