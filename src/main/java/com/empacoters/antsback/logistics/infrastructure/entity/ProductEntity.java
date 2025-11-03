package com.empacoters.antsback.logistics.infrastructure.entity;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private ProductFamilyEntity family;

    @Embedded
    private Dimensions dimensions;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private boolean fragile;

    private Double maxSupportedWeight;

    private String batch;
}
