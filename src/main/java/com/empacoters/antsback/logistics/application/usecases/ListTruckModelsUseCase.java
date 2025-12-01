package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<TruckModel> execute(Pageable pageable) {
        return truckModelRepository.findAll(pageable);
    }
    
    public Page<TruckModel> execute(String name, TruckType type, Pageable pageable) {
        return truckModelRepository.findAllWithFilters(name, type, pageable);
    }
}