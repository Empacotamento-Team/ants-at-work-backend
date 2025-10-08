package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.interfaces.dto.TruckCreateDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckResponseDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final GetTruckUseCase getTruckUseCase;
    private final ListTrucksUseCase listTrucksUseCase;
    private final CreateTruckUseCase createTruckUseCase;
    private final UpdateTruckUseCase updateTruckUseCase;
    private final DeleteTruckUseCase deleteTruckUseCase;

    public TruckController(
            GetTruckUseCase getTruckUseCase,
            ListTrucksUseCase listTrucksUseCase,
            CreateTruckUseCase createTruckUseCase,
            UpdateTruckUseCase updateTruckUseCase,
            DeleteTruckUseCase deleteTruckUseCase
    ) {
        this.getTruckUseCase = getTruckUseCase;
        this.listTrucksUseCase = listTrucksUseCase;
        this.createTruckUseCase = createTruckUseCase;
        this.updateTruckUseCase = updateTruckUseCase;
        this.deleteTruckUseCase = deleteTruckUseCase;
    }

    // GET /trucks?fleetId=...&status=...
    @GetMapping
    public ResponseEntity<List<TruckResponseDTO>> getAllTrucks(
            @RequestParam(required = false) Long fleetId,
            @RequestParam(required = false) String status
    ) {
        var truckStatus = TruckStatus.fromDescription(status).orElse(null);
        List<Truck> trucks = listTrucksUseCase.execute(fleetId, truckStatus);

        List<TruckResponseDTO> response = trucks.stream()
                .map(TruckResponseDTO::fromTruck)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // GET /trucks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TruckResponseDTO> getTruckById(@PathVariable Long id) {
        Truck truck = getTruckUseCase.execute(id);
        if (truck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TruckResponseDTO.fromTruck(truck));
    }

    // GET /trucks/types
    @GetMapping("/types")
    public ResponseEntity<TruckType[]> getTruckTypes() {
        return ResponseEntity.ok(TruckType.values());
    }

    // POST /trucks
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TruckResponseDTO> createTruck(@RequestBody TruckCreateDTO dto) {
        Truck created = createTruckUseCase.execute(
           dto.plate(),
           dto.maximumCapacity(),
           new Dimensions(dto.internalHeight(), dto.internalWidth(), dto.internalLength()),
           dto.type(),
           dto.status(),
           dto.currentMileage(),
           dto.details(),
           dto.maintenanceNote());

        return ResponseEntity
                .created(URI.create("/trucks/" + created.id()))
                .body(TruckResponseDTO.fromTruck(created));
    }

    // PUT /trucks/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TruckResponseDTO> updateTruck(@PathVariable Long id, @RequestBody TruckUpdateDTO dto) {
        // TODO: Melhorar validação para edição de dimensões.
        Dimensions newDimensions = null;
        if (dto.internalHeight() != null && dto.internalLength() != null && dto.internalWidth() != null)
            newDimensions = new Dimensions(dto.internalHeight(), dto.internalWidth(), dto.internalLength());

        Truck updated = updateTruckUseCase.execute(id,
                dto.plate(),
                dto.maximumCapacity(),
                newDimensions,
                dto.type(),
                dto.status(),
                dto.currentMileage(),
                dto.details(),
                dto.maintenanceNote()
        );

        return ResponseEntity.ok(TruckResponseDTO.fromTruck(updated));
    }

    // DELETE /trucks/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        deleteTruckUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}

