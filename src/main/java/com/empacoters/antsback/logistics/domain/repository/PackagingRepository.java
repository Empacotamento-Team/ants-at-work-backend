package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Packaging;

import java.util.List;

public interface PackagingRepository {
    List<Packaging> findAll();
    Packaging findById(Long id);
    Packaging save(Packaging packaging);
    void delete(Long id);
}
