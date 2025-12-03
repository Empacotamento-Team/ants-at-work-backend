package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPackagesUseCase {
    private final PackageRepository packageRepository;

    public ListPackagesUseCase(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> execute() {
        return packageRepository.findAll();
    }

    public List<Package> execute(Long loadId) {
        if (loadId != null) {
            return packageRepository.findByLoadId(loadId);
        }
        return packageRepository.findAll();
    }
}

