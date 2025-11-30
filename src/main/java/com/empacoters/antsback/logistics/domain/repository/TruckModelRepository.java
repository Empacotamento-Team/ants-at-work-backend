package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.TruckModel;

import java.util.List;

public interface TruckModelRepository {
    List<TruckModel> findAll();
    TruckModel findById(Long id);
    TruckModel save(TruckModel family);
    void delete(Long id);
}
