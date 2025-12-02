package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackagingRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class CreatePackagingUseCase {
    private final PackagingRepository packagingRepository;

    public CreatePackagingUseCase(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    public Packaging execute(CreatePackagingRequestDTO createPackagingRequest) {
        var dimensions = new Dimensions(createPackagingRequest.height(), createPackagingRequest.width(), createPackagingRequest.length());
        var newPackaging = new Packaging(
            null, createPackagingRequest.name(),
            createPackagingRequest.description(), dimensions
        );

        return packagingRepository.save(newPackaging);
    }
}
