package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.ProductFamilyMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductFamilyRepositoryImpl implements ProductFamilyRepository {
    private final SpringDataProductFamilyRepository springDataProductFamilyRepository;

    public ProductFamilyRepositoryImpl(SpringDataProductFamilyRepository springDataProductFamilyRepository) {
        this.springDataProductFamilyRepository = springDataProductFamilyRepository;
    }

    @Override
    public List<ProductFamily> findAll() {
        return springDataProductFamilyRepository.findAll()
                .stream()
                .map(ProductFamilyMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ProductFamily findById(Long id) {
        var entity = springDataProductFamilyRepository.findById(id).orElse(null);
        return ProductFamilyMapper.toDomain(entity);
    }

    @Override
    public ProductFamily save(ProductFamily productFamily) {
        var savedFamily = springDataProductFamilyRepository.save(ProductFamilyMapper.toEntity(productFamily));
        return ProductFamilyMapper.toDomain(savedFamily);
    }

    @Override
    public void delete(Long id) {
        springDataProductFamilyRepository.deleteById(id);
    }
}