package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.repository.PackageRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.PackageMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackageRepositoryImpl implements PackageRepository {
    private final SpringDataPackageRepository springDataPackageRepository;

    public PackageRepositoryImpl(SpringDataPackageRepository springDataPackageRepository) {
        this.springDataPackageRepository = springDataPackageRepository;
    }

    @Override
    public List<Package> findAll() {
        return springDataPackageRepository.findAll()
                .stream()
                .map(PackageMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Package findById(Long id) {
        var entity = springDataPackageRepository.findById(id).orElse(null);
        return PackageMapper.toDomain(entity);
    }

    @Override
    public List<Package> findByLoadId(Long loadId) {
        return springDataPackageRepository.findByLoadId(loadId)
                .stream()
                .map(PackageMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Package save(Package pkg) {
        var savedPackage = springDataPackageRepository.save(PackageMapper.toEntity(pkg));
        return PackageMapper.toDomain(savedPackage);
    }

    @Override
    public void delete(Long id) {
        springDataPackageRepository.deleteById(id);
    }
}