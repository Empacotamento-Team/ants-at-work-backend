package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.interfaces.dto.CreateTruckModelRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateTruckModelUseCase {
    private final TruckModelRepository truckModelRepository;

    public CreateTruckModelUseCase(TruckModelRepository truckModelRepository) {
        this.truckModelRepository = truckModelRepository;
    }

    public TruckModel execute(CreateTruckModelRequest request) {
        var modelDimensions = new Dimensions(
                request.getDefaultInternalHeight(),
                request.getDefaultInternalWidth(),
                request.getDefaultInternalLength()
        );

        TruckModel model = new TruckModel(
                null, request.getName(), request.getDescription(),
                request.getDefaultMaximumCapacity(), modelDimensions,
                request.getDefaultTruckType()
        );

        return truckModelRepository.save(model);
    }
}