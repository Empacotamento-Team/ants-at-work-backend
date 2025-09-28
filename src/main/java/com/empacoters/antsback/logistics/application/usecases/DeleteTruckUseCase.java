package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
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
        truckRepository.delete(id);
    }

}
