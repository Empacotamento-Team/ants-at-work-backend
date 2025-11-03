package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.infrastructure.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface SpringDataShipmentRepository extends JpaRepository<ShipmentEntity, Long> {
    List<ShipmentEntity> findByDateBetween(Date startDate, Date endDate);
}