package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackagingRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class UpdatePackagingUseCase {
    private final PackagingRepository packagingRepository;

    public UpdatePackagingUseCase(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    public Packaging execute(Long id, CreatePackagingRequestDTO createPackagingRequest) {
        var originalPackaging = this.packagingRepository.findById(id);
        if (originalPackaging == null) {
            throw new IllegalArgumentException("A embalagem solicitada não existe");
        }

        var originalDimensions = originalPackaging.internalDimensions();
        if (createPackagingRequest.name() != null && !createPackagingRequest.name().isEmpty()) {
            originalPackaging.changeName(createPackagingRequest.name());
        }

        if (createPackagingRequest.description() != null && !createPackagingRequest.description().isEmpty()) {
            originalPackaging.changeDescription(createPackagingRequest.description());
        }

        // Dimensions handling
        if (createPackagingRequest.height() != null) {
            originalDimensions.setHeight(createPackagingRequest.height());
        }

        if (createPackagingRequest.width() != null) {
            originalDimensions.setWidth(createPackagingRequest.width());
        }

        if (createPackagingRequest.length() != null) {
            originalDimensions.setLength(createPackagingRequest.length());
        }

        return this.packagingRepository.save(originalPackaging);
    }
}
