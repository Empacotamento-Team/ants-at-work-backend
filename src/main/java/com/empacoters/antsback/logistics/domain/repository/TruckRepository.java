package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;

import java.util.List;
import java.util.Optional;

public interface TruckRepository  {
    List<Truck> all();

    List<Truck> byFleetIdAndStatus(Optional<Long> fleetId, Optional<TruckStatus> status);

    List<Truck> fiveByFleetId(Long fleetId);

    Truck byId(Long id);

    Truck save(Truck truck);

    void delete(Long id);
}
