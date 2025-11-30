package com.empacoters.antsback.logistics.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "optimization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String solverStatus;

    private String terminationCondition;

    private boolean foundSolution;

    private Integer containersUsed;

    private Double familyPenality;

    private Double gravityCenterDeviation;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "optimization_queue_item_id")
    private OptimizationQueueItemEntity optimizationQueueItem;
}
