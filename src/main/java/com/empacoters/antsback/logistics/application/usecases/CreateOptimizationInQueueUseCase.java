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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                truck.id(), dimensions.length(), dimensions.width(),
                dimensions.height(), truck.maximumCapacity()
            );
        }).toArray(OptimizerRequestContainerRowDTO[]::new);
        // Converter dimensões de pacotes de cm para m (produtos em cm, containers em m)
        var packagesDto = packages.stream().map(pkg -> {
            // Dimensões do pacote estão em cm, converter para m
            double heightInM = pkg.packaging().internalDimensions().height() / 100.0;
            double widthInM = pkg.packaging().internalDimensions().width() / 100.0;
            double lengthInM = pkg.packaging().internalDimensions().length() / 100.0;
            
            return new OptimizerRequestItemRowDTO(
                pkg.id(), pkg.product().name(),
                pkg.product().family().id(), pkg.product().family().name(),
                pkg.product().batch(), pkg.product().weight(),
                pkg.product().maxSupportedWeight(), heightInM,
                widthInM, lengthInM
            );
        }).toArray(OptimizerRequestItemRowDTO[]::new);

        var requestDto = new OptimizerRequestDTO(packagesDto, containersDto);
        
        Map<String, Object> requestDataMap = new HashMap<>();
        requestDataMap.put("fleetId", optimizationRequestDTO.fleetId());
        requestDataMap.put("packagesIds", optimizationRequestDTO.packagesIds());
        requestDataMap.put("items", requestDto.items());
        requestDataMap.put("containers", requestDto.containers());
        
        ObjectMapper mapper = new ObjectMapper();
        String requestDataJson;
        try {
            requestDataJson = mapper.writeValueAsString(requestDataMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar dados da requisição", e);
        }
        
        return optimizationService.register(requestDto, requestDataJson);
    }
}
