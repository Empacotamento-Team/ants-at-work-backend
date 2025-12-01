package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.interfaces.dto.FleetResponseDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListFleetsUseCase {
    private final TruckRepository truckRepository;
    private final FleetRepository fleetRepository;

    public ListFleetsUseCase(TruckRepository truckRepository, FleetRepository fleetRepository) {
        this.truckRepository = truckRepository;
        this.fleetRepository = fleetRepository;
    }

    public FleetResponseDTO[] execute() {
        var fleetsDto = fleetRepository.listAll().stream().map(fleet -> {
            TruckResponseDTO[] trucks = truckRepository.fiveByFleetId(fleet.id())
                .stream().map(TruckResponseDTO::fromTruck).toArray(TruckResponseDTO[]::new);

            var trucksQuantity = truckRepository.countAllByFleetId(fleet.id());
            var activeTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.AVAILABLE);
            var maintenanceTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.UNDER_MAINTENANCE);

            return new FleetResponseDTO(
                fleet.id(),
                fleet.name(),
                fleet.codigo(),
                fleet.placeOfOperation(),
                trucksQuantity,
                activeTrucks,
                maintenanceTrucks,
                fleet.calculateAverageCapacity(),
                trucks
            );
        });

        return fleetsDto.toArray(FleetResponseDTO[]::new);
    }

    public Page<FleetResponseDTO> execute(Pageable pageable) {
        var fleetsPage = fleetRepository.listAll(pageable);
        var fleetsDto = fleetsPage.getContent().stream().map(fleet -> {
            TruckResponseDTO[] trucks = truckRepository.fiveByFleetId(fleet.id())
                .stream().map(TruckResponseDTO::fromTruck).toArray(TruckResponseDTO[]::new);

            var trucksQuantity = truckRepository.countAllByFleetId(fleet.id());
            var activeTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.AVAILABLE);
            var maintenanceTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.UNDER_MAINTENANCE);

            return new FleetResponseDTO(
                fleet.id(),
                fleet.name(),
                fleet.codigo(),
                fleet.placeOfOperation(),
                trucksQuantity,
                activeTrucks,
                maintenanceTrucks,
                fleet.calculateAverageCapacity(),
                trucks
            );
        }).toList();

        return new PageImpl<>(fleetsDto, pageable, fleetsPage.getTotalElements());
    }
    
    public Page<FleetResponseDTO> execute(String name, String truckPlate, Pageable pageable) {
        var fleetsPage = fleetRepository.listAllWithFilters(name, truckPlate, pageable);
        var fleetsDto = fleetsPage.getContent().stream().map(fleet -> {
            TruckResponseDTO[] trucks = truckRepository.fiveByFleetId(fleet.id())
                .stream().map(TruckResponseDTO::fromTruck).toArray(TruckResponseDTO[]::new);

            var trucksQuantity = truckRepository.countAllByFleetId(fleet.id());
            var activeTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.AVAILABLE);
            var maintenanceTrucks = truckRepository.countAllByFleetIdAndStatus(fleet.id(), TruckStatus.UNDER_MAINTENANCE);

            return new FleetResponseDTO(
                fleet.id(),
                fleet.name(),
                fleet.codigo(),
                fleet.placeOfOperation(),
                trucksQuantity,
                activeTrucks,
                maintenanceTrucks,
                fleet.calculateAverageCapacity(),
                trucks
            );
        }).toList();

        return new PageImpl<>(fleetsDto, pageable, fleetsPage.getTotalElements());
    }
}