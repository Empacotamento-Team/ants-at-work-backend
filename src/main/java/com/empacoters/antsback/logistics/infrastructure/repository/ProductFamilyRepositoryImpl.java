package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.ProductFamily;
import com.empacoters.antsback.logistics.domain.repository.ProductFamilyRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.ProductFamilyMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductFamily> findAll(Pageable pageable) {
        var entitiesPage = springDataProductFamilyRepository.findAll(pageable);
        var families = entitiesPage.getContent().stream()
                .map(ProductFamilyMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(families, pageable, entitiesPage.getTotalElements());
    }

    @Override
    public Page<ProductFamily> findAllWithFilters(String name, String description, Pageable pageable) {
        String nameFilter = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        String descriptionFilter = (description != null && !description.trim().isEmpty()) ? description.trim() : null;
        var entitiesPage = springDataProductFamilyRepository.findAllWithFilters(nameFilter, descriptionFilter, pageable);
        var families = entitiesPage.getContent().stream()
                .map(ProductFamilyMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(families, pageable, entitiesPage.getTotalElements());
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