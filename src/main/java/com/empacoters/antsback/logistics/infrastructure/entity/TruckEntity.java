package com.empacoters.antsback.logistics.infrastructure.entity;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import com.empacoters.antsback.logistics.domain.model.TruckStatus;
import com.empacoters.antsback.logistics.domain.model.TruckType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "trucks")
@NoArgsConstructor
@AllArgsConstructor
public class TruckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    private String plate;

    @Column(nullable = false)
    private Integer maximumCapacity;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "height", column = @Column(name = "internal_height")),
        @AttributeOverride(name = "width", column = @Column(name = "internal_width")),
        @AttributeOverride(name = "length", column = @Column(name = "internal_length")),
    })
    private Dimensions internalDimensions;

    @Enumerated(EnumType.STRING)
    private TruckType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'AVAILABLE'")
    private TruckStatus status;

    @Column(name = "last_revision")
    private LocalDate lastRevision;

    @Column(name = "current_mileage", nullable = false)
    private Double currentMileage;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceRecordEntity> maintenanceHistory = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fleet_id")
    private FleetEntity fleet;
}
