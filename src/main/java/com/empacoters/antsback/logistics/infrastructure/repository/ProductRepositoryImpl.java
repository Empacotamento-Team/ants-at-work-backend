package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Product;
import com.empacoters.antsback.logistics.domain.repository.ProductRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.ProductMapper;
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