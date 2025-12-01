package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.TruckModel;
import com.empacoters.antsback.logistics.interfaces.dto.CreateTruckModelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/truck-models")
public class TruckModelController {
    private final ListTruckModelsUseCase listTruckModelsUseCase;
    private final GetTruckModelUseCase getTruckModelUseCase;
    private final CreateTruckModelUseCase createTruckModelUseCase;
    private final UpdateTruckModelUseCase updateTruckModelUseCase;
    private final DeleteTruckModelUseCase deleteTruckModelUseCase;

    public TruckModelController(
        ListTruckModelsUseCase listTruckModelsUseCase,
        GetTruckModelUseCase getTruckModelUseCase,
        CreateTruckModelUseCase createTruckModelUseCase,
        UpdateTruckModelUseCase updateTruckModelUseCase,
        DeleteTruckModelUseCase deleteTruckModelUseCase
    ) {
        this.listTruckModelsUseCase = listTruckModelsUseCase;
        this.getTruckModelUseCase = getTruckModelUseCase;
        this.createTruckModelUseCase = createTruckModelUseCase;
        this.updateTruckModelUseCase = updateTruckModelUseCase;
        this.deleteTruckModelUseCase = deleteTruckModelUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<TruckModel>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        var truckType = com.empacoters.antsback.logistics.domain.model.TruckType.fromDescription(type).orElse(null);
        var result = listTruckModelsUseCase.execute(name, truckType, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckModel> getById(@PathVariable Long id) {
        var result = getTruckModelUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TruckModel> create(@RequestBody CreateTruckModelRequest request) {
        var created = createTruckModelUseCase.execute(request);
        return ResponseEntity.created(URI.create("/truck-models/" + created.id()))
            .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckModel> update(@RequestBody CreateTruckModelRequest request, @PathVariable Long id) {
        var updated = updateTruckModelUseCase.execute(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteTruckModelUseCase.execute(id);
    }
}