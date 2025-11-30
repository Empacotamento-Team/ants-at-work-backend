package com.empacoters.antsback.logistics.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "packaging_id")
    private PackagingEntity packaging;

    @ManyToOne
    @JoinColumn(name = "load_id")
    private LoadEntity load;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Double supportedWeight;

    private Double xPosition;

    private Double yPosition;

    private Double zPosition;

    private String orientation;
}
