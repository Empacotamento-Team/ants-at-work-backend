package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final SpringDataProductRepository springDataProductRepository;

    public ProductRepositoryImpl(SpringDataProductRepository springDataProductRepository) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll()
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        var entitiesPage = springDataProductRepository.findAll(pageable);
        var products = entitiesPage.getContent().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(products, pageable, entitiesPage.getTotalElements());
    }

    @Override
    public Page<Product> findAllWithFilters(String name, Long familyId, String batch, Boolean fragile, Pageable pageable) {
        String nameFilter = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        String batchFilter = (batch != null && !batch.trim().isEmpty()) ? batch.trim() : null;
        var entitiesPage = springDataProductRepository.findAllWithFilters(nameFilter, familyId, batchFilter, fragile, pageable);
        var products = entitiesPage.getContent().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(products, pageable, entitiesPage.getTotalElements());
    }

    @Override
    public List<Product> findByBatch(String batch) {
        return springDataProductRepository.findByBatchEquals(batch)
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        var entity = springDataProductRepository.findById(id).orElse(null);
        return ProductMapper.toDomain(entity);
    }

    @Override
    public List<Product> findByFamilyId(Long familyId) {
        return springDataProductRepository.findByFamilyId(familyId)
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product save(Product product) {
        var savedProduct = springDataProductRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(savedProduct);
    }

    @Override
    public void delete(Long productId) {
        springDataProductRepository.deleteById(productId);
    }
}