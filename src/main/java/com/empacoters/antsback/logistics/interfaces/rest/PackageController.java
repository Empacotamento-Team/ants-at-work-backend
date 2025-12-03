package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.*;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackageRequest;
import com.empacoters.antsback.logistics.interfaces.dto.PackageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final ListPackagesUseCase listPackagesUseCase;
    private final GetPackageUseCase getPackageUseCase;
    private final CreatePackageUseCase createPackageUseCase;
    private final UpdatePackageUseCase updatePackageUseCase;
    private final DeletePackageUseCase deletePackageUseCase;

    public PackageController(
            ListPackagesUseCase listPackagesUseCase,
            GetPackageUseCase getPackageUseCase,
            CreatePackageUseCase createPackageUseCase,
            UpdatePackageUseCase updatePackageUseCase,
            DeletePackageUseCase deletePackageUseCase) {
        this.listPackagesUseCase = listPackagesUseCase;
        this.getPackageUseCase = getPackageUseCase;
        this.createPackageUseCase = createPackageUseCase;
        this.updatePackageUseCase = updatePackageUseCase;
        this.deletePackageUseCase = deletePackageUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PackageResponseDTO>> list(@RequestParam(required = false) Long loadId) {
        List<Package> packages = loadId != null 
                ? listPackagesUseCase.execute(loadId)
                : listPackagesUseCase.execute();
        
        List<PackageResponseDTO> response = packages.stream()
                .map(PackageResponseDTO::fromPackage)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-load/{loadId}")
    public ResponseEntity<List<PackageResponseDTO>> listByLoad(@PathVariable Long loadId) {
        List<Package> packages = listPackagesUseCase.execute(loadId);
        List<PackageResponseDTO> response = packages.stream()
                .map(PackageResponseDTO::fromPackage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> get(@PathVariable Long id) {
        Package pkg = getPackageUseCase.execute(id);
        return ResponseEntity.ok(PackageResponseDTO.fromPackage(pkg));
    }

    @PostMapping
    public ResponseEntity<PackageResponseDTO> create(@RequestBody CreatePackageRequest request) {
        Package pkg;
        
        if (request.getPackagingName() != null && !request.getPackagingName().trim().isEmpty()) {
            pkg = createPackageUseCase.executeWithNewPackaging(request);
        } else if (request.getPackagingId() != null) {
            pkg = createPackageUseCase.execute(
                    request.getPackagingId(),
                    request.getProductId()
            );
        } else {
            throw new IllegalArgumentException("Deve fornecer packagingId ou criar uma nova embalagem");
        }
        
        return ResponseEntity.ok(PackageResponseDTO.fromPackage(pkg));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CreatePackageRequest request) {
        Package pkg = updatePackageUseCase.execute(id, request);
        return ResponseEntity.ok(PackageResponseDTO.fromPackage(pkg));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePackageUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
