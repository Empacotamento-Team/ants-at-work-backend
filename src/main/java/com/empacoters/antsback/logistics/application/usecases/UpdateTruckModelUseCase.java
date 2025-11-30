package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.logistics.interfaces.dto.CreateTruckModelRequest;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UpdateTruckModelUseCase {
    private final TruckModelRepository truckModelRepository;

    public UpdateTruckModelUseCase(TruckModelRepository truckModelRepository) {
        this.truckModelRepository = truckModelRepository;
    }

    public TruckModel execute(Long id, CreateTruckModelRequest updateTruckModelRequest) {
        var model = truckModelRepository.findById(id);
        if (model == null) {
            throw new BadRequestException("O modelo de caminhão solicitado não existe");
        }

        if (updateTruckModelRequest.getName() != null && !updateTruckModelRequest.getName().isEmpty()) {
            model.changeName(updateTruckModelRequest.getName());
        }

        if (updateTruckModelRequest.getDescription() != null && !updateTruckModelRequest.getDescription().isEmpty()) {
            model.changeDescription(updateTruckModelRequest.getDescription());
        }

        if (updateTruckModelRequest.getDefaultMaximumCapacity() != null) {
            model.changeDefaultMaximumCapacity(updateTruckModelRequest.getDefaultMaximumCapacity());
        }

        if (updateTruckModelRequest.getDefaultTruckType() != null) {
            model.changeDefaultTruckType(updateTruckModelRequest.getDefaultTruckType());
        }

        // Dimensions

        var dimensions = model.defaultInternalDimensions() != null
            ? model.defaultInternalDimensions()
            : new Dimensions();

        if (updateTruckModelRequest.getDefaultInternalHeight() != null) {
            dimensions.changeHeight(updateTruckModelRequest.getDefaultInternalHeight());
        }

        if (updateTruckModelRequest.getDefaultInternalWidth() != null) {
            dimensions.changeWidth(updateTruckModelRequest.getDefaultInternalWidth());
        }

        if (updateTruckModelRequest.getDefaultInternalLength() != null) {
            dimensions.changeLength(updateTruckModelRequest.getDefaultInternalLength());
        }

        return truckModelRepository.save(model);
    }
}
