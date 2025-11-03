package com.empacoters.antsback.logistics.infrastructure.repository;

import com.empacoters.antsback.logistics.domain.model.Shipment;
import com.empacoters.antsback.logistics.domain.repository.ShipmentRepository;
import com.empacoters.antsback.logistics.infrastructure.mapper.ShipmentMapper;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {
    private final SpringDataShipmentRepository springDataShipmentRepository;

    public ShipmentRepositoryImpl(SpringDataShipmentRepository springDataShipmentRepository) {
        this.springDataShipmentRepository = springDataShipmentRepository;
    }

    @Override
    public List<Shipment> findAll() {
        return springDataShipmentRepository.findAll()
                .stream()
                .map(ShipmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Shipment findById(Long id) {
        var entity = springDataShipmentRepository.findById(id).orElse(null);
        return ShipmentMapper.toDomain(entity);
    }

    @Override
    public List<Shipment> findByDateRange(Date startDate, Date endDate) {
        return springDataShipmentRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(ShipmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Shipment save(Shipment shipment) {
        var savedShipment = springDataShipmentRepository.save(ShipmentMapper.toEntity(shipment));
        return ShipmentMapper.toDomain(savedShipment);
    }

    @Override
    public void delete(Long shipmentId) {
        springDataShipmentRepository.deleteById(shipmentId);
    }
}