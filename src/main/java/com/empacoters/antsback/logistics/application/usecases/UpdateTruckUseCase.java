package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UpdateTruckUseCase {
    private final TruckRepository truckRepository;

    public UpdateTruckUseCase(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public Truck execute(
            Long truckId,
            String plate,
            Integer maximumCapacity,
            Float internalVolume,
            Set<TruckType> types,
            TruckStatus status,
            Float currentMileage,
            String details
    ) {
        Truck truck = truckRepository.byId(truckId);
        truck.update(plate, maximumCapacity, internalVolume, types, status, currentMileage, details);

        return truckRepository.save(truck);
    }
}
