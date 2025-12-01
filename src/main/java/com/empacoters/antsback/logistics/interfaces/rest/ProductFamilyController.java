package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.DeleteProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListProductFamiliesUseCase;
import com.empacoters.antsback.logistics.application.usecases.UpdateProductFamilyUseCase;
import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.interfaces.dto.CreateProductFamilyRequest;
import com.empacoters.antsback.logistics.interfaces.dto.UpdateProductFamilyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-families")
public class ProductFamilyController {
    private final GetProductFamilyUseCase getProductFamilyUseCase;
    private final ListProductFamiliesUseCase listProductFamiliesUseCase;
    private final CreateProductFamilyUseCase createProductFamilyUseCase;
    private final UpdateProductFamilyUseCase updateProductFamilyUseCase;
    private final DeleteProductFamilyUseCase deleteProductFamilyUseCase;

    public ProductFamilyController(
            GetProductFamilyUseCase getProductFamilyUseCase,
            ListProductFamiliesUseCase listProductFamiliesUseCase,
            CreateProductFamilyUseCase createProductFamilyUseCase,
            UpdateProductFamilyUseCase updateProductFamilyUseCase,
            DeleteProductFamilyUseCase deleteProductFamilyUseCase) {
        this.getProductFamilyUseCase = getProductFamilyUseCase;
        this.listProductFamiliesUseCase = listProductFamiliesUseCase;
        this.createProductFamilyUseCase = createProductFamilyUseCase;
        this.updateProductFamilyUseCase = updateProductFamilyUseCase;
        this.deleteProductFamilyUseCase = deleteProductFamilyUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<ProductFamily>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(listProductFamiliesUseCase.execute(name, description, pageable));
    }

    @GetMapping("/{id}")
    public ProductFamily getById(@PathVariable Long id) {
        return getProductFamilyUseCase.execute(id);
    }

    @PostMapping
    public ProductFamily create(@RequestBody CreateProductFamilyRequest request) {
        ProductFamily family = new ProductFamily(
            null,
            request.getName(),
            request.getDescription(),
            request.getDefaultMaxSupportedWeight()
        );

        return createProductFamilyUseCase.execute(family);
    }

    @PutMapping("/{id}")
    public ProductFamily update(@PathVariable Long id, @RequestBody UpdateProductFamilyRequest request) {
        return updateProductFamilyUseCase.execute(
            id,
            request.getName(),
            request.getDescription(),
            request.getDefaultMaxSupportedWeight()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteProductFamilyUseCase.execute(id);
    }
}