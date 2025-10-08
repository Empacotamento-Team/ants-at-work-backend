package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Fleet;

import java.util.List;

public interface FleetRepository {
    List<Fleet> listAll();

    Fleet findById(Long id);

    Fleet findByCode(String code);

    Fleet save(Fleet fleetToSave);

    void delete(Long id);
}
