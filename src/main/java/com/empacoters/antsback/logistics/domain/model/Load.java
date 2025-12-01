package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Load {
    private final Long id;
    private Long shipmentId;
    private Truck relatedTruck;
    private List<Package> packages;
    private Double totalAllocatedWeight;
    private Double remainingWeight;
    private Double totalAllocatedVolume;
    private Double volumeOccupationPercentage;
    private Double xPosition;
    private Double yPosition;
    private Double zPosition;

    public Load(Long id, Long shipmentId, Truck relatedTruck, List<Package> packages, Double totalAllocatedWeight, Double remainingWeight, Double totalAllocatedVolume, Double xPosition, Double yPosition, Double zPosition) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.relatedTruck = validateTruck(relatedTruck);
        this.packages = validatePackages(packages);
        this.totalAllocatedWeight = totalAllocatedWeight;
        this.remainingWeight = remainingWeight;
        this.totalAllocatedVolume = totalAllocatedVolume;
        this.volumeOccupationPercentage = totalAllocatedVolume / totalAllocatedWeight;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
    }

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("shipmentId")
    public Long shipmentId() {
        return shipmentId;
    }

    public void changeShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @JsonProperty("relatedTruck")
    public Truck relatedTruck() {
        return relatedTruck;
    }

    public void changeRelatedTruck(Truck relatedTruck) {
        this.relatedTruck = validateTruck(relatedTruck);
    }

    @JsonProperty("packages")
    public List<Package> packages() {
        return packages;
    }

    public void changePackages(List<Package> packages) {
        this.packages = validatePackages(packages);
    }

    @JsonProperty("totalAllocatedWeight")
    public Double totalAllocatedWeight() {
        return totalAllocatedWeight;
    }

    public void changeTotalAllocatedWeight(Double totalAllocatedWeight) {
        this.totalAllocatedWeight = totalAllocatedWeight;
    }

    @JsonProperty("remainingWeight")
    public Double remainingWeight() {
        return remainingWeight;
    }

    public void changeRemainingWeight(Double remainingWeight) {
        this.remainingWeight = remainingWeight;
    }

    @JsonProperty("totalAllocatedVolume")
    public Double totalAllocatedVolume() {
        return totalAllocatedVolume;
    }

    public void changeTotalAllocatedVolume(Double totalAllocatedVolume) {
        this.totalAllocatedVolume = totalAllocatedVolume;
    }

    @JsonProperty("volumeOccupationPercentage")
    public Double volumeOccupationPercentage() {
        return volumeOccupationPercentage;
    }

    public void changeVolumeOccupationPercentage(Double volumeOccupationPercentage) {
        this.volumeOccupationPercentage = volumeOccupationPercentage;
    }

    @JsonProperty("xPosition")
    public Double xPosition() {
        return xPosition;
    }

    public void changeXPosition(Double xPosition) {
        this.xPosition = xPosition;
    }

    @JsonProperty("yPosition")
    public Double yPosition() {
        return yPosition;
    }

    public void changeYPosition(Double yPosition) {
        this.yPosition = yPosition;
    }

    @JsonProperty("zPosition")
    public Double zPosition() {
        return zPosition;
    }

    public void changeZPosition(Double zPosition) {
        this.zPosition = zPosition;
    }

    // Validations
    private Truck validateTruck(Truck truck) {
        if (truck == null)
            throw new IllegalStateException("O caminhão relacionado a carga não pode ser nulo");
        return truck;
    }

    private List<Package> validatePackages(List<Package> packages) {
        if (packages == null)
            throw new IllegalArgumentException("A lista de pacotes relacionados a carga não pode ser nula");
        return packages;
    }
}
