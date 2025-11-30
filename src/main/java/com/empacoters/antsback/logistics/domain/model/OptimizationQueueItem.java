package com.empacoters.antsback.logistics.domain.model;

import java.time.Instant;

public class OptimizationQueueItem {
    private final Long id;
    private OptimizationStatus status;
    private Integer attempts;
    private String requestData;

    private Instant createdAt;
    private Instant updatedAt;

    public OptimizationQueueItem(Long id, OptimizationStatus status, Integer attempts, String requestData, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.status = status;
        this.attempts = attempts;
        this.requestData = requestData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public OptimizationStatus status() {
        return status;
    }

    public void changeStatus(OptimizationStatus status) {
        this.status = status;
    }

    public Integer attempts() {
        return attempts;
    }

    public void changeAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String requestData() {
        return requestData;
    }

    public void modifyRequestData(String requestData) {
        this.requestData = requestData;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void changeUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
