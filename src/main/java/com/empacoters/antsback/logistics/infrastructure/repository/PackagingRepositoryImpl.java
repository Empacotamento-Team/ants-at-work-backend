package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Packaging;
import com.empacoters.antsback.logistics.domain.repository.PackagingRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.PackagingMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackagingRepositoryImpl implements PackagingRepository {
    private final SpringDataPackagingRepository springDataPackagingRepository;

    public PackagingRepositoryImpl(SpringDataPackagingRepository springDataPackagingRepository) {
        this.springDataPackagingRepository = springDataPackagingRepository;
    }

    @Override
    public List<Packaging> findAll() {
        return springDataPackagingRepository.findAll()
                .stream()
                .map(PackagingMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Packaging findById(Long id) {
        var entity = springDataPackagingRepository.findById(id).orElse(null);
        return PackagingMapper.toDomain(entity);
    }

    @Override
    public Packaging save(Packaging packaging) {
        var savedPackaging = springDataPackagingRepository.save(PackagingMapper.toEntity(packaging));
        return PackagingMapper.toDomain(savedPackaging);
    }

    @Override
    public void delete(Long id) {
        springDataPackagingRepository.deleteById(id);
    }
}