package com.empacoters.antsback.logistics.infrastructure.entity;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "truck_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double defaultMaximumCapacity;

    private Dimensions defaultInternalDimensions;

    private TruckType defaultTruckType;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;
}
