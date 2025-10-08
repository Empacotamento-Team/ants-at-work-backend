package com.empacoters.antsback.logistics.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "fleets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FleetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String codigo;

    private String placeOfOperation;

    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TruckEntity> trucks = new ArrayList<>();
}
