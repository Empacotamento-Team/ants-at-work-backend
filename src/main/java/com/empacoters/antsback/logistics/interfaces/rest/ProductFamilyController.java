package com.empacoters.antsback.logistics.interfaces.rest;

import com.empacoters.antsback.logistics.application.usecases.CreateProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.DeleteProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.GetProductFamilyUseCase;
import com.empacoters.antsback.logistics.application.usecases.ListProductFamiliesUseCase;
import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.interfaces.dto.CreateProductFamilyRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-families")
public class ProductFamilyController {
    private final GetProductFamilyUseCase getProductFamilyUseCase;
    private final ListProductFamiliesUseCase listProductFamiliesUseCase;
    private final CreateProductFamilyUseCase createProductFamilyUseCase;
    private final DeleteProductFamilyUseCase deleteProductFamilyUseCase;

    public ProductFamilyController(
            GetProductFamilyUseCase getProductFamilyUseCase,
            ListProductFamiliesUseCase listProductFamiliesUseCase,
            CreateProductFamilyUseCase createProductFamilyUseCase,
            DeleteProductFamilyUseCase deleteProductFamilyUseCase) {
        this.getProductFamilyUseCase = getProductFamilyUseCase;
        this.listProductFamiliesUseCase = listProductFamiliesUseCase;
        this.createProductFamilyUseCase = createProductFamilyUseCase;
        this.deleteProductFamilyUseCase = deleteProductFamilyUseCase;
    }

    @GetMapping
    public List<ProductFamily> list() {
        return listProductFamiliesUseCase.execute();
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteProductFamilyUseCase.execute(id);
    }
}