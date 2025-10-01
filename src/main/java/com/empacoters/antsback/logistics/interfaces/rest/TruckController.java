package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.Truck;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.interfaces.dto.MaintenanceRecordDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckCreateDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckResponseDTO;
import com.empacoters.antsback.logistics.interfaces.dto.TruckUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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
            @RequestParam(required = false) TruckStatus status
    ) {
        List<Truck> trucks = listTrucksUseCase.execute(Optional.ofNullable(fleetId), Optional.ofNullable(status));

        List<TruckResponseDTO> response = trucks.stream()
                .map(this::toResponseDTO)
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
        return ResponseEntity.ok(toResponseDTO(truck));
    }

    // POST /trucks
    @PostMapping
    public ResponseEntity<TruckResponseDTO> createTruck(@RequestBody TruckCreateDTO dto) {
        Truck created = createTruckUseCase.execute(
           dto.plate(),
           dto.maximumCapacity(),
           new Dimensions(dto.internalHeight(), dto.internalWidth(), dto.internalLength()),
           dto.types(),
           dto.status(),
           dto.currentMileage(),
           dto.details(),
           dto.maintenanceNote());

        return ResponseEntity
                .created(URI.create("/trucks/" + created.id()))
                .body(toResponseDTO(created));
    }

    // PUT /trucks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TruckResponseDTO> updateTruck(@PathVariable Long id, @RequestBody TruckUpdateDTO dto) {
        // TODO: Melhorar validação para edição de dimensões.
        Dimensions newDimensions = null;
        if (dto.internalHeight() != null && dto.internalLength() != null && dto.internalWidth() != null)
            newDimensions = new Dimensions(dto.internalHeight(), dto.internalWidth(), dto.internalLength());

        Truck updated = updateTruckUseCase.execute(id,
                Optional.ofNullable(dto.plate()),
                Optional.ofNullable(dto.maximumCapacity()),
                Optional.ofNullable(newDimensions),
                Optional.ofNullable(dto.types()),
                Optional.ofNullable(dto.status()),
                Optional.ofNullable(dto.currentMileage()),
                Optional.ofNullable(dto.details()),
                Optional.ofNullable(dto.maintenanceNote()));

        return ResponseEntity.ok(toResponseDTO(updated));
    }

    // DELETE /trucks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        deleteTruckUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    // ---- conversor manual para ResponseDTO ----
    private TruckResponseDTO toResponseDTO(Truck truck) {
        // converte cada MaintenanceRecord para DTO
        List<MaintenanceRecordDTO> maintenanceDTOs = truck.maintenanceHistory().stream().map(record -> new MaintenanceRecordDTO(null, record.date(), record.description())).collect(Collectors.toList());

        return new TruckResponseDTO(
                truck.id(),
                truck.plate(),
                truck.maximumCapacity(),
                truck.internalDimensions().height(),
                truck.internalDimensions().width(),
                truck.internalDimensions().length(),
                truck.types(),
                truck.status(),
                truck.lastRevision(),
                truck.currentMileage(),
                truck.details(),
                maintenanceDTOs // agora é List<MaintenanceRecordDTO>
        );
    }

}

