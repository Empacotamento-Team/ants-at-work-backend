package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateOptimizationInQueueUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetOptimizationUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListOptimizationQueueItemsUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListOptimizationsUseCase;
import com.empacoters.antsback.logistics.domain.model.Optimization;
import com.empacoters.antsback.logistics.domain.model.OptimizationQueueItem;
import com.empacoters.antsback.logistics.interfaces.dto.OptimizationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/optimization")
public class OptimizationController {
    private final ListOptimizationsUseCase listOptimizationsUseCase;
    private final GetOptimizationUseCase getOptimizationUseCase;
    private final CreateOptimizationInQueueUseCase createOptimizationInQueueUseCase;
    private final ListOptimizationQueueItemsUseCase listOptimizationQueueItemsUseCase;

    public OptimizationController(ListOptimizationsUseCase listOptimizationsUseCase, GetOptimizationUseCase getOptimizationUseCase, CreateOptimizationInQueueUseCase createOptimizationInQueueUseCase, ListOptimizationQueueItemsUseCase listOptimizationQueueItemsUseCase) {
        this.listOptimizationsUseCase = listOptimizationsUseCase;
        this.getOptimizationUseCase = getOptimizationUseCase;
        this.createOptimizationInQueueUseCase = createOptimizationInQueueUseCase;
        this.listOptimizationQueueItemsUseCase = listOptimizationQueueItemsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Optimization>> getOptimizations() {
        var optimizations = this.listOptimizationsUseCase.execute();
        return ResponseEntity.ok(optimizations);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optimization> getOptimizationById(@PathVariable Long id) {
        var opt = this.getOptimizationUseCase.execute(id);
        return ResponseEntity.ok(opt);
    }

    @PostMapping
    public ResponseEntity<OptimizationQueueItem> registerOptimization(@RequestBody OptimizationRequestDTO optimizationRequestDTO) {
        var queueItem = createOptimizationInQueueUseCase.execute(optimizationRequestDTO);

        return ResponseEntity.created(URI.create("/optimization/queue-items/" + queueItem.id())).body(queueItem);
    }

    @GetMapping("/queue-items")
    public ResponseEntity<List<OptimizationQueueItem>> getQueueItems() {
        var items = listOptimizationQueueItemsUseCase.execute();
        return ResponseEntity.ok(items);
    }
}
