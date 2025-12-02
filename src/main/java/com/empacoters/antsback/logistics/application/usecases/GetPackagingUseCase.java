package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPackagingUseCase {
    private final PackagingRepository packagingRepository;

    public GetPackagingUseCase(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    public Packaging execute(Long id) {
        return this.packagingRepository.findById(id);
    }
}
