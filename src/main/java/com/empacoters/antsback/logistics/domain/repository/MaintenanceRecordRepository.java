package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.MaintenanceRecord;

import java.util.List;

public interface MaintenanceRecordRepository {
    List<MaintenanceRecord> all();

    List<MaintenanceRecord> byTruckId(Long truckId);

    MaintenanceRecord save(MaintenanceRecord record);

    void delete(Long id);
}
