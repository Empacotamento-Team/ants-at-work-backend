package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.interfaces.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/fleets")
public class FleetController {
    private final ListFleetsUseCase listFleetsUseCase;
    private final AddTrucksToFleetUseCase addTrucksToFleetUseCase;
    private final CreateFleetUseCase createFleetUseCase;
    private final UpdateFleetUseCase updateFleetUseCase;
    private final GetFleetUseCase getFleetUseCase;
    private final RemoveTruckFromFleetUseCase removeTruckFromFleetUseCase;
    private final DeleteFleetUseCase deleteFleetUseCase;

    public FleetController(
            ListFleetsUseCase listFleetsUseCase,
            AddTrucksToFleetUseCase addTrucksToFleetUseCase,
            CreateFleetUseCase createFleetUseCase,
            UpdateFleetUseCase updateFleetUseCase,
            GetFleetUseCase getFleetUseCase,
            RemoveTruckFromFleetUseCase removeTrucksFromFleetUseCase,
            DeleteFleetUseCase deleteFleetUseCase
    ) {
        this.listFleetsUseCase = listFleetsUseCase;
        this.addTrucksToFleetUseCase = addTrucksToFleetUseCase;
        this.createFleetUseCase = createFleetUseCase;
        this.updateFleetUseCase = updateFleetUseCase;
        this.getFleetUseCase = getFleetUseCase;
        this.removeTruckFromFleetUseCase = removeTrucksFromFleetUseCase;
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

    @DeleteMapping("/{id}/trucks")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteTrucks(@PathVariable Long id, @RequestBody DeleteTruckFromFleetDTO deleteTruckFromFleetDTO) {
        System.out.println(Arrays.toString(deleteTruckFromFleetDTO.truckIds()));
        this.removeTruckFromFleetUseCase.execute(id, deleteTruckFromFleetDTO.truckIds());
        return ResponseEntity.noContent().build();
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
