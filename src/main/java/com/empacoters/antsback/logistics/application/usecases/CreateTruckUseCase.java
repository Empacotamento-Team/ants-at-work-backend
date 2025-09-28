package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CreateTruckUseCase {

    private final TruckRepository truckRepository;

    public CreateTruckUseCase(TruckRepository truckRepository) {

        this.truckRepository = truckRepository;

    }

    public Truck execute(String plate, Integer maximumCapacity, Float internalVolume, Set<TruckType> types, TruckStatus status, Float currentMileage, String details)
    {
        TruckStatus initialStatus = (status != null) ? status : TruckStatus.AVAILABLE;
        return truckRepository.save(new Truck(null, plate, maximumCapacity, internalVolume, types, initialStatus, null, currentMileage, details));
    }
}
