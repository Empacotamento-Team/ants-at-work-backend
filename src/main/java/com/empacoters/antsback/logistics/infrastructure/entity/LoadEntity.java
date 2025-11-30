package com.empacoters.antsback.logistics.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "loads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAllocatedWeight;

    private Double remainingWeight;

    private Double totalAllocatedVolume;

    private Double volumeOccupationPercentage;

    private Double xPosition;

    private Double yPosition;

    private Double zPosition;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private TruckEntity relatedTruck;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private ShipmentEntity shipment;

    @OneToMany(mappedBy = "load", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<PackageEntity> packages;
}
