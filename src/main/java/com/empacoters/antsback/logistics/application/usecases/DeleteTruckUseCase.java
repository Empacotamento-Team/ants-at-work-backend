package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTruckUseCase {
    public final TruckRepository truckRepository;

    public DeleteTruckUseCase(TruckRepository truckRepository)
    {
        this.truckRepository = truckRepository;
    }
    public void execute(Long id)
    {
        var truck = truckRepository.byId(id);

        if (truck == null) {
            throw new NotFoundException("O caminhão solicitado não existe");
        }
        truckRepository.delete(id);
    }

}
