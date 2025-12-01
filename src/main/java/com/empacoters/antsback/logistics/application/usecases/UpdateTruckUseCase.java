package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.*;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UpdateTruckUseCase {
    private final TruckRepository truckRepository;
    private final TruckModelRepository truckModelRepository;
    private final StartMaintenanceUseCase startMaintenanceUseCase;
    private final FinishMaintenanceUseCase finishMaintenanceUseCase;

    public UpdateTruckUseCase(TruckRepository truckRepository, TruckModelRepository truckModelRepository, StartMaintenanceUseCase startMaintenanceUseCase, FinishMaintenanceUseCase finishMaintenanceUseCase) {
        this.truckRepository = truckRepository;
        this.truckModelRepository = truckModelRepository;
        this.startMaintenanceUseCase = startMaintenanceUseCase;
        this.finishMaintenanceUseCase = finishMaintenanceUseCase;
    }

    public Truck execute(
        Long truckId, String plate, Double maximumCapacity,
        Dimensions internalDimensions, TruckType type,
        TruckStatus status, Double currentMileage,
        String details, String maintenanceNote, Long modelId
    ) {
        Truck truck = truckRepository.byId(truckId);
        if (truck == null) return null;
        TruckModel model = modelId != null ? truckModelRepository.findById(modelId) : null;
        TruckStatus oldStatus = truck.status();
        truck.update(plate, maximumCapacity, internalDimensions, type, status, currentMileage, details, null, model);
        if (status != null && oldStatus != status) {
            if (status == TruckStatus.UNDER_MAINTENANCE) {
                startMaintenanceUseCase.execute(truck.id(), String.valueOf(maintenanceNote));
            } else if (status == TruckStatus.AVAILABLE) {
                finishMaintenanceUseCase.execute(truck.id(), String.valueOf(maintenanceNote));
            } else if (status == TruckStatus.UNAVAILABLE) {
                finishMaintenanceUseCase.execute(truck.id(), String.valueOf(maintenanceNote));
            }

        }
        return truckRepository.save(truck);
    }
}
