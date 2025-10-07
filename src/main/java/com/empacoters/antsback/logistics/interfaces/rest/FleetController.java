package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.interfaces.dto.AddTruckToFleetDTO;
import com.empacoters.antsback.logistics.interfaces.dto.FleetCreateDTO;
import com.empacoters.antsback.logistics.interfaces.dto.FleetResponseDTO;
import com.empacoters.antsback.logistics.interfaces.dto.FleetUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/fleets")
public class FleetController {
    private final ListFleetsUseCase listFleetsUseCase;
    private final AddTrucksToFleetUseCase addTrucksToFleetUseCase;
    private final CreateFleetUseCase createFleetUseCase;
    private final UpdateFleetUseCase updateFleetUseCase;
    private final GetFleetUseCase getFleetUseCase;
    private final DeleteFleetUseCase deleteFleetUseCase;

    public FleetController(
            ListFleetsUseCase listFleetsUseCase,
            AddTrucksToFleetUseCase addTrucksToFleetUseCase,
            CreateFleetUseCase createFleetUseCase,
            UpdateFleetUseCase updateFleetUseCase,
            GetFleetUseCase getFleetUseCase,
            DeleteFleetUseCase deleteFleetUseCase
    ) {
        this.listFleetsUseCase = listFleetsUseCase;
        this.addTrucksToFleetUseCase = addTrucksToFleetUseCase;
        this.createFleetUseCase = createFleetUseCase;
        this.updateFleetUseCase = updateFleetUseCase;
        this.getFleetUseCase = getFleetUseCase;
        this.deleteFleetUseCase = deleteFleetUseCase;
    }

    @GetMapping
    public ResponseEntity<FleetResponseDTO[]> getAllFleets() {
        var dtos = listFleetsUseCase.execute();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FleetResponseDTO> getFleetById(@PathVariable Long id) {
        var dto = getFleetUseCase.execute(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FleetResponseDTO> createFleet(@RequestBody @Valid FleetCreateDTO fleetCreateDTO) {
        var newFleet = createFleetUseCase.execute(
            fleetCreateDTO.getName(),
            fleetCreateDTO.getCode(),
            fleetCreateDTO.getPlaceOfOperation(),
            fleetCreateDTO.getTrucksIds()
        );
        return ResponseEntity
            .created(URI.create("/fleets/" + newFleet.id()))
            .body(FleetResponseDTO.fromFleet(newFleet));
    }

    @PostMapping("/{id}/trucks")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> assignTrucks(@PathVariable Long id, @RequestBody AddTruckToFleetDTO addTruckToFleetDTO) {
        this.addTrucksToFleetUseCase.execute(id, addTruckToFleetDTO.trucksIds());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<FleetResponseDTO> updateFleet(@RequestBody FleetUpdateDTO fleetUpdateDTO) {
        var newFleet = updateFleetUseCase.execute(
            fleetUpdateDTO.id(),
            fleetUpdateDTO.name(),
            fleetUpdateDTO.code(),
            fleetUpdateDTO.placeOfOperation(),
            fleetUpdateDTO.trucksIds()
        );
        return ResponseEntity
            .ok(FleetResponseDTO.fromFleet(newFleet));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteFleet(@PathVariable long id) {
        deleteFleetUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
