package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPackagingsUseCase {
    private final PackagingRepository packagingRepository;

    public ListPackagingsUseCase(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    public List<Packaging> execute() {
        return packagingRepository.findAll();
    }
}
