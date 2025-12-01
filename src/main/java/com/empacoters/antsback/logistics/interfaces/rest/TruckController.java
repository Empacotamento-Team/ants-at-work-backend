package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import com.empacoters.antsback.logistics.interfaces.dto.TruckCreateDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckResponseDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // GET /trucks?fleetId=...&status=...&plate=...&type=...&modelId=...&page=...&size=...
    @GetMapping
    public ResponseEntity<Page<TruckResponseDTO>> getAllTrucks(
            @RequestParam(required = false) Long fleetId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String plate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long modelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Truck> trucksPage;
        
        if (fleetId != null) {
            var truckStatus = TruckStatus.fromDescription(status).orElse(null);
            trucksPage = listTrucksUseCase.execute(fleetId, truckStatus, pageable);
        } else {
            var truckStatus = TruckStatus.fromDescription(status).orElse(null);
            var truckType = TruckType.fromDescription(type).orElse(null);
            trucksPage = listTrucksUseCase.execute(plate, truckType, truckStatus, modelId, pageable);
        }

        Page<TruckResponseDTO> response = trucksPage.map(TruckResponseDTO::fromTruck);

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
        System.out.println("=== DEBUG CONTROLLER ===");
        System.out.println("DTO modelId: " + dto.modelId());
        System.out.println("DTO completo: plate=" + dto.plate() + ", modelId=" + dto.modelId());
        
        Truck created = createTruckUseCase.execute(
           dto.plate(),
           dto.maximumCapacity(),
           new Dimensions(dto.internalHeight(), dto.internalWidth(), dto.internalLength()),
           dto.type(),
           dto.status(),
           dto.currentMileage(),
           dto.details(),
           dto.maintenanceNote(),
           dto.modelId()
        );
        
        System.out.println("Truck criado no controller - model: " + (created.model() != null ? created.model().id() : "null"));
        System.out.println("=== FIM DEBUG CONTROLLER ===");

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
                dto.maintenanceNote(),
                dto.modelId()
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

