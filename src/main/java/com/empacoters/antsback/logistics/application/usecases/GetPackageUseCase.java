package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetPackageUseCase {
    private final PackageRepository packageRepository;

    public GetPackageUseCase(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Package execute(Long id) {
        var pkg = packageRepository.findById(id);
        if (pkg == null) {
            throw new NotFoundException("O pacote solicitado não existe");
        }
        return pkg;
    }
}
