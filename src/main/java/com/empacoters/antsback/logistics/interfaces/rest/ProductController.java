package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.DeleteProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListProductsUseCase;
import com.empacoters.antsback.logistics.application.usecases.UpdateProductUseCase;
import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.interfaces.dto.CreateProductRequest;
import com.empacoters.antsback.logistics.interfaces.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(
            GetProductUseCase getProductUseCase,
            ListProductsUseCase listProductsUseCase,
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {
        this.getProductUseCase = getProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long familyId,
            @RequestParam(required = false) String batch,
            @RequestParam(required = false) Boolean fragile,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(listProductsUseCase.execute(name, familyId, batch, fragile, pageable));
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return getProductUseCase.execute(id);
    }

    @GetMapping("/by-family/{familyId}")
    public List<Product> getByFamilyId(@PathVariable Long familyId) {
        return listProductsUseCase.byFamilyId(familyId);
    }

    @PostMapping
    public Product create(@RequestBody CreateProductRequest request) {
        ProductFamily family = new ProductFamily(
            request.getFamilyId(),
            null,
            null,
            null
        );

        Product product = new Product(
            null,
            request.getName(),
            family,
            request.getDimensions(),
            request.getWeight(),
            request.getMaxSupportedWeight(),
            request.getBatch(),
            request.isFragile()
        );

        return createProductUseCase.execute(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        return updateProductUseCase.execute(
            id,
            request.getName(),
            request.getFamilyId(),
            request.getDimensions(),
            request.getWeight(),
            request.getMaxSupportedWeight(),
            request.getBatch(),
            request.isFragile()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteProductUseCase.execute(id);
    }
}