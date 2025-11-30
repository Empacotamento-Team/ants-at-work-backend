package com.empacoters.antsback.logistics.domain.model;

import java.time.Instant;

public class Optimization {
    private final Long id;
    private final Long optimizationQueueItemId;
    private final Instant createdAt;
    private String solverStatus;
    private String terminationCondition;
    private boolean foundSolution;
    private Integer containersUsed;
    private Double familyPenality;
    private Double gravityCenterDeviation;

    public Optimization(Long id, Long optimizationQueueItemId, String solverStatus, String terminationCondition, boolean foundSolution, Integer containersUsed, Double familyPenality, Double gravityCenterDeviation, Instant createdAt) {
        this.id = id;
        this.optimizationQueueItemId = optimizationQueueItemId;
        this.solverStatus = solverStatus;
        this.terminationCondition = terminationCondition;
        this.foundSolution = foundSolution;
        this.containersUsed = containersUsed;
        this.familyPenality = familyPenality;
        this.gravityCenterDeviation = gravityCenterDeviation;
        this.createdAt = createdAt;
    }

    public Long id() {
        return id;
    }

    public Long optimizationQueueItemId() {
        return optimizationQueueItemId;
    }

    public String solverStatus() {
        return solverStatus;
    }

    public void changeSolverStatus(String solverStatus) {
        this.solverStatus = solverStatus;
    }

    public String terminationCondition() {
        return terminationCondition;
    }

    public void changeTerminationCondition(String terminationCondition) {
        this.terminationCondition = terminationCondition;
    }

    public boolean foundSolution() {
        return foundSolution;
    }

    public void changeFoundSolution(boolean foundSolution) {
        this.foundSolution = foundSolution;
    }

    public Integer containersUsed() {
        return containersUsed;
    }

    public void changeContainersUsed(Integer containersUsed) {
        this.containersUsed = containersUsed;
    }

    public Double familyPenality() {
        return familyPenality;
    }

    public void changeFamilyPenality(Double familyPenality) {
        this.familyPenality = familyPenality;
    }

    public Double gravityCenterDeviation() {
        return gravityCenterDeviation;
    }

    public void changeGravityCenterDeviation(Double gravityCenterDeviation) {
        this.gravityCenterDeviation = gravityCenterDeviation;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
