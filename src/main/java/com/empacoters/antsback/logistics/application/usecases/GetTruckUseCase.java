package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetTruckUseCase {

    private final TruckRepository truckRepository;

    public GetTruckUseCase(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }


    public Truck execute(Long id) {
        return truckRepository.byId(id);
    }

    public List<Truck> execute(Optional<Long> fleetId, Optional<TruckStatus> status) {
        return truckRepository.byFleetIdAndStatus(fleetId, status);
    }
}
