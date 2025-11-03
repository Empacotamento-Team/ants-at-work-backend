package com.empacoters.antsback.logistics.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
        name = "product_package",
        joinColumns = @JoinColumn(name = "package_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;

    private Double supportedWeight;
}
