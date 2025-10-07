package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTruckUseCase {

    private final TruckRepository truckRepository;
    private final StartMaintenanceUseCase startMaintenanceUseCase;
    public CreateTruckUseCase(TruckRepository truckRepository,StartMaintenanceUseCase startMaintenanceUseCase) {

        this.truckRepository = truckRepository;
        this.startMaintenanceUseCase = startMaintenanceUseCase;

    }

    public Truck execute(String plate, Integer maximumCapacity, Dimensions internalDimensions, TruckType type, TruckStatus status, Double currentMileage, String details, String maintenanceNote)
    {
        Truck truck = new Truck(null, plate, maximumCapacity, internalDimensions, type, status, null, currentMileage, details, null);
        Truck savedTruck = truckRepository.save(truck);

        // TODO: completar lógica
        TruckStatus initialStatus;
        if(status != null)
        {
            startMaintenanceUseCase.execute(savedTruck.id(), maintenanceNote);
            initialStatus = status;
        }
        else
        {
            initialStatus = TruckStatus.AVAILABLE;
        }

        return savedTruck;
    }
}
