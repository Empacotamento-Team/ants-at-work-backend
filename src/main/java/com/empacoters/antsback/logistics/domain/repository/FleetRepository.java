package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Fleet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FleetRepository {
    List<Fleet> listAll();
    Page<Fleet> listAll(Pageable pageable);
    Page<Fleet> listAllWithFilters(String name, String truckPlate, Pageable pageable);

    Fleet findById(Long id);

    Fleet findByCode(String code);

    Fleet save(Fleet fleetToSave);

    void delete(Long id);
}
