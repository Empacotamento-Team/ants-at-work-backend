package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTruckUseCase {
    private final TruckRepository truckRepository;
    private final StartMaintenanceUseCase startMaintenanceUseCase;
    private final FinishMaintenanceUseCase finishMaintenanceUseCase;

    public UpdateTruckUseCase(TruckRepository truckRepository,StartMaintenanceUseCase startMaintenanceUseCase, FinishMaintenanceUseCase finishMaintenanceUseCase) {
        this.truckRepository = truckRepository;
        this.startMaintenanceUseCase = startMaintenanceUseCase;
        this.finishMaintenanceUseCase = finishMaintenanceUseCase;
    }

    public Truck execute(
        Long truckId, String plate, Integer maximumCapacity,
        Dimensions internalDimensions, TruckType type,
        TruckStatus status, Double currentMileage,
        String details, String maintenanceNote
    ) {
        Truck truck = truckRepository.byId(truckId);
        if (truck == null) return null;
        TruckStatus oldStatus = truck.status();
        truck.update(plate, maximumCapacity, internalDimensions, type, status, currentMileage, details, null);
        if (status != null && oldStatus != status) {
            if (status == TruckStatus.UNDER_MAINTENANCE) {
                startMaintenanceUseCase.execute(truck.id(), String.valueOf(maintenanceNote));
            } else if (status == TruckStatus.AVAILABLE) {
                finishMaintenanceUseCase.execute(truck.id(), String.valueOf(maintenanceNote));
            }
        }
        return truckRepository.save(truck);
    }
}
