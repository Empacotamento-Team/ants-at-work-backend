package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.*;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CreateTruckUseCase {

    private final TruckRepository truckRepository;
    private final TruckModelRepository truckModelRepository;
    private final StartMaintenanceUseCase startMaintenanceUseCase;
    public CreateTruckUseCase(TruckRepository truckRepository, TruckModelRepository truckModelRepository, StartMaintenanceUseCase startMaintenanceUseCase) {

        this.truckRepository = truckRepository;
        this.truckModelRepository = truckModelRepository;
        this.startMaintenanceUseCase = startMaintenanceUseCase;

    }

    public Truck execute(String plate, Double maximumCapacity, Dimensions internalDimensions, TruckType type, TruckStatus status, Double currentMileage, String details, String maintenanceNote, Long modelId)
    {
        var existingTruck = truckRepository.byPlate(plate);
        if (existingTruck != null) {
            throw new BadRequestException("Um caminhão com essa placa já foi cadastrado");
        }

        TruckModel model = null;

        if (modelId != null) {
            model = truckModelRepository.findById(modelId);
            if (model == null) {
                throw new BadRequestException("Modelo informado não existe.");
            }
        }

        Truck truck = new Truck(
            null, plate, maximumCapacity,
            internalDimensions, type, status,
            null, currentMileage, details,
            null, model
        );
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
