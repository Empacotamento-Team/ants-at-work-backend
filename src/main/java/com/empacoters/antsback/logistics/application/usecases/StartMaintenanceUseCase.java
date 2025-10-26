package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StartMaintenanceUseCase {
    private final TruckRepository truckRepository;

    public StartMaintenanceUseCase(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public void execute(Long truckId, String notes) {
        Truck truck = truckRepository.byId(truckId);
        MaintenanceRecord record = new MaintenanceRecord(null,LocalDate.now(),notes);
        truck.addMaintenceRecord(record);
        truck.changeTruckStatus(TruckStatus.UNDER_MAINTENANCE);
    }
}
