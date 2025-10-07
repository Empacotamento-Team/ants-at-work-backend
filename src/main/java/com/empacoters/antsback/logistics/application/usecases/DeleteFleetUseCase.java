package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.FleetRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteFleetUseCase {
    private final FleetRepository fleetRepository;

    public DeleteFleetUseCase(FleetRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    public void execute(Long fleetId) {
        var fleet = fleetRepository.findById(fleetId);
        if (fleet == null) {
            throw new NotFoundException("A frota solicitada não foi encontrada");
        }
        fleetRepository.delete(fleetId);
    }
}
