package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackagingRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/packaging")
public class PackagingController {
    private final ListPackagingsUseCase listPackagingsUseCase;
    private final GetPackagingUseCase getPackagingUseCase;
    private final CreatePackagingUseCase createPackagingUseCase;
    private final UpdatePackagingUseCase updatePackagingUseCase;
    private final DeletePackagingUseCase deletePackagingUseCase;

    public PackagingController(ListPackagingsUseCase listPackagingsUseCase, GetPackagingUseCase getPackagingUseCase, CreatePackagingUseCase createPackagingUseCase, UpdatePackagingUseCase updatePackagingUseCase, DeletePackagingUseCase deletePackagingUseCase) {
        this.listPackagingsUseCase = listPackagingsUseCase;
        this.getPackagingUseCase = getPackagingUseCase;
        this.createPackagingUseCase = createPackagingUseCase;
        this.updatePackagingUseCase = updatePackagingUseCase;
        this.deletePackagingUseCase = deletePackagingUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Packaging>> getPackagings() {
        var packagings = listPackagingsUseCase.execute();
        return ResponseEntity.ok(packagings);
    }

    @GetMapping("{id}")
    public ResponseEntity<Packaging> getPackagingById(@PathVariable Long id) {
        var packaging = getPackagingUseCase.execute(id);
        return ResponseEntity.ok(packaging);
    }

    @PostMapping
    public ResponseEntity<Packaging> createPackaging(@RequestBody CreatePackagingRequestDTO createPackagingRequestDTO) {
        var created = createPackagingUseCase.execute(createPackagingRequestDTO);
        return ResponseEntity.created(URI.create("/packaging/" + created.id())).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Packaging> updatePackaging(@PathVariable Long id, @RequestBody CreatePackagingRequestDTO createPackagingRequestDTO) {
        var packaging = this.updatePackagingUseCase.execute(id, createPackagingRequestDTO);
        return ResponseEntity.ok(packaging);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePackaging(@PathVariable Long id) {
        this.deletePackagingUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
