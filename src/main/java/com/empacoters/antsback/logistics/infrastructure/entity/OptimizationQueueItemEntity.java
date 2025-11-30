package com.empacoters.antsback.logistics.infrastructure.entity;

import com.empacoters.antsback.logistics.domain.model.OptimizationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "optimization_queue_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizationQueueItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OptimizationStatus status;

    private Integer attempts;

    @Column(columnDefinition = "TEXT")
    private String requestData;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant updatedAt;
}
