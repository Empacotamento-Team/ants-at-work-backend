package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.TruckModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTruckModelRepository extends JpaRepository<TruckModelEntity, Long> { }
