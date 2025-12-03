package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreatePackageUseCase;
import com.empacoters.antsback.logistics.application.usecases.DeletePackageUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetPackageUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListPackagesUseCase;
import com.empacoters.antsback.logistics.application.usecases.UpdatePackageUseCase;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.interfaces.dto.CreatePackageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final ListPackagesUseCase listPackagesUseCase;
    private final GetPackageUseCase getPackageUseCase;
    private final CreatePackageUseCase createPackageUseCase;
    private final UpdatePackageUseCase updatePackageUseCase;
    private final DeletePackageUseCase deletePackageUseCase;

    public PackageController(ListPackagesUseCase listPackagesUseCase, GetPackageUseCase getPackageUseCase,
                            CreatePackageUseCase createPackageUseCase, UpdatePackageUseCase updatePackageUseCase,
                            DeletePackageUseCase deletePackageUseCase) {
        this.listPackagesUseCase = listPackagesUseCase;
        this.getPackageUseCase = getPackageUseCase;
        this.createPackageUseCase = createPackageUseCase;
        this.updatePackageUseCase = updatePackageUseCase;
        this.deletePackageUseCase = deletePackageUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Package>> getPackages() {
        var packages = listPackagesUseCase.execute();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/by-load/{loadId}")
    public ResponseEntity<List<Package>> getPackagesByLoadId(@PathVariable Long loadId) {
        var packages = listPackagesUseCase.findByLoadId(loadId);
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        var pkg = getPackageUseCase.execute(id);
        return ResponseEntity.ok(pkg);
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody CreatePackageRequest request) {
        Package created;

        // Se packagingId foi fornecido, usar packaging existente
        if (request.getPackagingId() != null) {
            created = createPackageUseCase.execute(
                request.getPackagingId(),
                request.getProductId(),
                request.getSupportedWeight()
            );
        }
        // Se os campos de packaging foram fornecidos, criar nova packaging
        else if (request.getPackagingName() != null && request.getPackagingHeight() != null &&
                 request.getPackagingWidth() != null && request.getPackagingLength() != null) {
            created = createPackageUseCase.executeWithNewPackaging(
                request.getPackagingName(),
                request.getPackagingDescription(),
                request.getPackagingHeight(),
                request.getPackagingWidth(),
                request.getPackagingLength(),
                request.getProductId(),
                request.getSupportedWeight()
            );
        } else {
            throw new IllegalArgumentException("Você deve fornecer um packagingId ou os dados para criar uma nova embalagem (packagingName, packagingHeight, packagingWidth, packagingLength)");
        }

        return ResponseEntity.created(URI.create("/packages/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody CreatePackageRequest request) {
        var pkg = updatePackageUseCase.execute(
            id,
            request.getPackagingId(),
            request.getProductId(),
            request.getSupportedWeight()
        );
        return ResponseEntity.ok(pkg);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        deletePackageUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
