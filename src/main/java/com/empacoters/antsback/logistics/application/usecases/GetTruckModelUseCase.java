package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetTruckModelUseCase {
    private final TruckModelRepository truckModelRepository;

    public GetTruckModelUseCase(TruckModelRepository truckModelRepository) {
        this.truckModelRepository = truckModelRepository;
    }

    public TruckModel execute(Long id) {
        var model = truckModelRepository.findById(id);
        if (model == null) {
            throw new NotFoundException("O modelo de caminhões solicitado não existe");
        }
        return model;
    }
}