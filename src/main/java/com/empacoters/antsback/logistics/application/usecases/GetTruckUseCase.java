package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTruckUseCase {

    private final TruckRepository truckRepository;

    public GetTruckUseCase(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }


    public Truck execute(Long id) {
        return truckRepository.byId(id);
    }
}
