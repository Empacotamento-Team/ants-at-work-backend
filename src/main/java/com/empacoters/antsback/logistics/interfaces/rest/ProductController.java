package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.DeleteProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetProductUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListProductsUseCase;
import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.interfaces.dto.CreateProductRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(
            GetProductUseCase getProductUseCase,
            ListProductsUseCase listProductsUseCase,
            CreateProductUseCase createProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {
        this.getProductUseCase = getProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.createProductUseCase = createProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @GetMapping
    public List<Product> list() {
        return listProductsUseCase.execute();
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteProductUseCase.execute(id);
    }
}