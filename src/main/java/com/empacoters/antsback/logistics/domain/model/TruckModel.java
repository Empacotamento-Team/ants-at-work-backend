package com.empacoters.antsback.logistics.domain.model;

public class TruckModel {
    private final Long id;
    private String name;
    private String description;

    private Double defaultMaximumCapacity;
    private Dimensions defaultInternalDimensions;
    private TruckType defaultTruckType;

    public TruckModel(Long id, String name, String description, Double defaultMaximumCapacity, Dimensions defaultInternalDimensions, TruckType defaultTruckType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultMaximumCapacity = defaultMaximumCapacity;
        this.defaultInternalDimensions = defaultInternalDimensions;
        this.defaultTruckType = defaultTruckType;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
    public void changeName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }
    public void changeDescription(String description) {
        this.description = description;
    }

    public Double defaultMaximumCapacity() {
        return defaultMaximumCapacity;
    }
    public void changeDefaultMaximumCapacity(Double defaultMaximumCapacity) {
        this.defaultMaximumCapacity = defaultMaximumCapacity;
    }

    public Dimensions defaultInternalDimensions() {
        return defaultInternalDimensions;
    }
    public void changeDefaultInternalDimensions(Dimensions defaultInternalDimensions) {
        this.defaultInternalDimensions = defaultInternalDimensions;
    }

    public TruckType defaultTruckType() {
        return defaultTruckType;
    }
    public void changeDefaultTruckType(TruckType defaultTruckType) {
        this.defaultTruckType = defaultTruckType;
    }
}
