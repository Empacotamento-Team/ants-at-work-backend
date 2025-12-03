package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeletePackageUseCase {
    private final PackageRepository packageRepository;

    public DeletePackageUseCase(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void execute(Long id) {
        var pkg = packageRepository.findById(id);
        if (pkg == null) {
            throw new NotFoundException("O pacote solicitado não existe");
        }
        packageRepository.delete(id);
    }
}
