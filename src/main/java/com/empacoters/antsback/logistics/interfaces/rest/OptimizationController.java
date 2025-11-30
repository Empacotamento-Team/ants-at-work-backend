package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.domain.repository.OptimizationQueueItemRepository;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.logistics.domain.repository.TruckRepository;
import com.empacoters.antsback.logistics.domain.service.OptimizationService;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestContainerRowDTO;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestDTO;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizerRequestItemRowDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/optimization")
public class OptimizationController {
    private final OptimizationService optimizationService;
    private final TruckRepository truckRepository;
    private final PackageRepository packageRepository;
    private final OptimizationQueueItemRepository optimizationQueueItemRepository;

    public OptimizationController(OptimizationService optimizationService, TruckRepository truckRepository, PackageRepository packageRepository, OptimizationQueueItemRepository optimizationQueueItemRepository) {
        this.optimizationService = optimizationService;
        this.truckRepository = truckRepository;
        this.packageRepository = packageRepository;
        this.optimizationQueueItemRepository = optimizationQueueItemRepository;
    }

    @PostMapping
    public ResponseEntity<Void> registerOptimization() {
        var container1 = this.truckRepository.byId(1L);
        var container2 = this.truckRepository.byId(2L);
        var container3 = this.truckRepository.byId(3L);
        List<OptimizerRequestContainerRowDTO> containers = new ArrayList<>();

        for (var container : List.of(container1, container2, container3)) {
            var dto = new OptimizerRequestContainerRowDTO(
                container.id(), container.internalDimensions().width(),
                container.internalDimensions().height(), container.internalDimensions().length(),
                container.maximumCapacity().doubleValue()
            );
            containers.add(dto);
        }

        var item1 = this.packageRepository.findById(1L);
        var item2 = this.packageRepository.findById(2L);
        var item3 = this.packageRepository.findById(3L);
        List<OptimizerRequestItemRowDTO> items = new ArrayList<>();

        for (var item : List.of(item1, item2, item3)) {
            var dto = new OptimizerRequestItemRowDTO(
                item.id(), item.product().name(), item.product().family().id(),
                item.product().family().name(), item.product().batch(),
                item.product().weight(), item.supportedWeight(),
                item.packaging().internalDimensions().height(),
                item.packaging().internalDimensions().width(),
                item.packaging().internalDimensions().length()
            );
            items.add(dto);
        }

        var request = new OptimizerRequestDTO(
            items.toArray(OptimizerRequestItemRowDTO[]::new),
            containers.toArray(OptimizerRequestContainerRowDTO[]::new)
        );

        optimizationService.register(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> processOptimization() {
        var queueItem = this.optimizationQueueItemRepository.findById(2L);
        optimizationService.process(queueItem);
        return ResponseEntity.ok().build();
    }
}
