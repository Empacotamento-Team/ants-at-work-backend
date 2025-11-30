package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.TruckModelRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTruckModelUseCase {
    private final TruckModelRepository truckModelRepository;

    public DeleteTruckModelUseCase(TruckModelRepository truckModelRepository) {
        this.truckModelRepository = truckModelRepository;
    }

    public void execute(Long id) {
        var family = truckModelRepository.findById(id);
        if (family == null) {
            throw new NotFoundException("O modelo de caminhões solicitado não existe");
        }
        truckModelRepository.delete(id);
    }
}