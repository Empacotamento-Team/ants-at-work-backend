package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TruckModelRepository {
    List<TruckModel> findAll();
    Page<TruckModel> findAll(Pageable pageable);
    Page<TruckModel> findAllWithFilters(String name, TruckType type, Pageable pageable);
    TruckModel findById(Long id);
    TruckModel save(TruckModel family);
    void delete(Long id);
}
