package com.empacoters.antsback.logistics.domain.repository;

import com.empacoters.antsback.logistics.domain.model.Package;

import java.util.List;

public interface PackageRepository {
    List<Package> findAll();
    List<Package> findByLoadId(Long loadId);
    Package findById(Long id);
    Package save(Package pkg);
    void delete(Long id);
}
