package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("status")
    public OptimizationStatus status() {
        return status;
    }

    public void changeStatus(OptimizationStatus status) {
        this.status = status;
    }

    @JsonProperty("attempts")
    public Integer attempts() {
        return attempts;
    }

    public void changeAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    @JsonProperty("requestData")
    public String requestData() {
        return requestData;
    }

    public void modifyRequestData(String requestData) {
        this.requestData = requestData;
    }

    @JsonProperty("createdAt")
    public Instant createdAt() {
        return createdAt;
    }

    @JsonProperty("updatedAt")
    public Instant updatedAt() {
        return updatedAt;
    }

    public void changeUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
