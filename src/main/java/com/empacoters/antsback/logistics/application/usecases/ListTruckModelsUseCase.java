package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTruckModelsUseCase {
    private final TruckModelRepository truckModelRepository;

    public ListTruckModelsUseCase(TruckModelRepository truckModelRepository) {
        this.truckModelRepository = truckModelRepository;
    }

    public List<TruckModel> execute() {
        return truckModelRepository.findAll();
    }
}