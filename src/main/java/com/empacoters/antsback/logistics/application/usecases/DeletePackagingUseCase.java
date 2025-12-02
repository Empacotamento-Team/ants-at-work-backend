package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import org.springframework.stereotype.Service;

@Service
public class DeletePackagingUseCase {
    private final PackagingRepository packagingRepository;

    public DeletePackagingUseCase(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    public void execute(Long id) {
        packagingRepository.delete(id);
    }
}
