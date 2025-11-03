package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.Load;
import com.empacoters.antsback.logistics.domain.repository.LoadRepository;
import com.empacoters.antsback.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetLoadUseCase {
    private final LoadRepository loadRepository;

    public GetLoadUseCase(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    public Load execute(Long id) {
        var load = loadRepository.findById(id);
        if (load == null) {
            throw new NotFoundException("A carga solicitada não existe");
        }
        return load;
    }
}