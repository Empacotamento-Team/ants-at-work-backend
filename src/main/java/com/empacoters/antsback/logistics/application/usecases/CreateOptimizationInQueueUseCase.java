package com.empacoters.antsback.logistics.application.usecases;

import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.domain.service.OptimizationService;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizationRequestDTO;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestContainerRowDTO;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestDTO;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestItemRowDTO;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOptimizationInQueueUseCase {
    private final OptimizationService optimizationService;
    private final TruckRepository truckRepository;
    private final PackageRepository packageRepository;

    public CreateOptimizationInQueueUseCase(OptimizationService optimizationService, TruckRepository truckRepository, PackageRepository packageRepository) {
        this.optimizationService = optimizationService;
        this.truckRepository = truckRepository;
        this.packageRepository = packageRepository;
    }

    public OptimizationQueueItem execute(OptimizationRequestDTO optimizationRequestDTO) {
        var containers = truckRepository.byFleetIdAndStatus(optimizationRequestDTO.fleetId(), TruckStatus.AVAILABLE);
        if (containers.isEmpty()) {
            throw new BadRequestException("A frota selecionada para otimização não possui caminhões disponíveis");
        }

        List<Package> packages = new ArrayList<>();
        for (var pkgId : optimizationRequestDTO.packagesIds()) {
            var pkg = packageRepository.findById(pkgId);
            if (pkg == null)
                continue;
            packages.add(pkg);
        }
        if (packages.isEmpty()) {
            throw new BadRequestException("Nenhum pacote válido foi fornecido");
        }

        var containersDto = containers.stream().map(truck -> {
            var dimensions = truck.internalDimensions();
            return new OptimizerRequestContainerRowDTO(
                truck.id(), dimensions.width(), dimensions.height(),
                dimensions.length(), truck.maximumCapacity()
            );
        }).toArray(OptimizerRequestContainerRowDTO[]::new);
        var packagesDto = packages.stream().map(pkg -> new OptimizerRequestItemRowDTO(
            pkg.id(), pkg.product().name(),
                pkg.product().family().id(), pkg.product().family().name(),
                pkg.product().batch(), pkg.product().weight(),
                pkg.product().maxSupportedWeight(), pkg.packaging().internalDimensions().height(),
                pkg.packaging().internalDimensions().width(), pkg.packaging().internalDimensions().length()
        )).toArray(OptimizerRequestItemRowDTO[]::new);

        var requestDto = new OptimizerRequestDTO(packagesDto, containersDto);
        return optimizationService.register(requestDto);
    }
}
