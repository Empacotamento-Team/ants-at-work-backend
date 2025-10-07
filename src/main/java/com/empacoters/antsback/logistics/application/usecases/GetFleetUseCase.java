package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.interfaces.dto.FleetResponseDTO;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetFleetUseCase {
    private final FleetRepository fleetRepository;
    private final TruckRepository truckRepository;

    public GetFleetUseCase(FleetRepository fleetRepository, TruckRepository truckRepository) {
        this.fleetRepository = fleetRepository;
        this.truckRepository = truckRepository;
    }

    public FleetResponseDTO execute(Long fleetId) {
        var fleet = fleetRepository.findById(fleetId);

        if (fleet == null) {
            throw new NotFoundException("Não foi possível encontrar a frota solicitada");
        }

        int trucksQuantity = 0;
        int activeTrucks = 0;
        int underMaintenanceTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.UNDER_MAINTENANCE);

        return new FleetResponseDTO(
            fleet.id(),
            fleet.name(),
            fleet.codigo(),
            trucksQuantity,
            activeTrucks,
            underMaintenanceTrucks,
            fleet.calculateAverageCapacity(),
            null
        );
    }
}
