package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;

import java.util.List;

public interface TruckRepository  {
    List<Truck> all();

    List<Truck> byIdIn(List<Long> ids);

    List<Truck> byFleetIdAndStatus(Long fleetId, TruckStatus status);

    List<Truck> fiveByFleetId(Long fleetId);

    Truck byId(Long id);

    Integer countAllByFleetId(Long fleetId);

    Integer countAllByFleetIdAndStatus(Long fleetId, TruckStatus status);

    Truck save(Truck truck);

    void delete(Long id);
}
