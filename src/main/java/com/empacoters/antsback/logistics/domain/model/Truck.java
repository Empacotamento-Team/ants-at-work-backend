package com.empacoters.antsback.logistics.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Truck{
    private final Long id;
    private String plate;
    private Double maximumCapacity;
    private Dimensions internalDimensions;
    private TruckType type;
    private TruckStatus status;
    private LocalDate lastRevision;
    private Double currentMileage;
    private String details;
    private List<MaintenanceRecord> maintenanceHistory;
    private Fleet fleet;
    private TruckModel model;;

    public Truck(Long id, String plate, Double maximumCapacity, Dimensions internalDimensions, TruckType type, TruckStatus status, LocalDate lastRevision, Double currentMileage, String details, Fleet fleet, TruckModel model) {
        this.id = id;
        this.plate = plate;
        this.maximumCapacity = maximumCapacity;
        this.internalDimensions = internalDimensions;
        this.type = type;
        this.status = status;
        this.lastRevision = lastRevision;
        this.currentMileage = currentMileage;
        this.details = details;
        this.maintenanceHistory = new ArrayList<>();
        this.fleet = fleet;
        this.model = model;
    }

    @JsonProperty("id")
    public Long id() {return this.id;}

    @JsonProperty("plate")
    public String plate() {return this.plate;}

    @JsonProperty("maximumCapacity")
    public Double maximumCapacity(){ return this.maximumCapacity;}

    @JsonProperty("internalDimensions")
    public Dimensions internalDimensions() {
        return this.internalDimensions;
    }

    @JsonProperty("type")
    public TruckType type() {return this.type;}

    @JsonProperty("model")
    public TruckModel model() { return this.model; }

    @JsonProperty("status")
    public TruckStatus status(){return this.status;}

    @JsonProperty("lastRevision")
    public LocalDate lastRevision(){return this.lastRevision;}

    @JsonProperty("currentMileage")
    public Double currentMileage(){return this.currentMileage;}

    @JsonProperty("details")
    public String details(){return this.details;}

    @JsonProperty("maintenanceHistory")
    public List<MaintenanceRecord> maintenanceHistory(){return this.maintenanceHistory;}

    @JsonProperty("fleet")
    public Fleet fleet(){return this.fleet;}

    public void changeTruckStatus(TruckStatus newStatus)
    {
        this.status = newStatus;
    }
    public void changeLastRevision(LocalDate newDate)
    {
        this.lastRevision = newDate;
    }
    public void changeModel(TruckModel model) { this.model = model; }
    public void addMaintenceRecord(MaintenanceRecord record)
    {
        maintenanceHistory.add(record);
    }
    public void changeFleet(Fleet newFleet) {
        this.fleet = newFleet;
    }
    public void update(
            String plate,
            Double maximumCapacity,
            Dimensions internalDimensions,
            TruckType type,
            TruckStatus status,
            Double currentMileage,
            String details,
            Fleet fleet,
            TruckModel model
    ) {
        if (plate != null && !plate.isEmpty()) {
            this.plate = plate;
        }
        if (maximumCapacity != null) {
            this.maximumCapacity = maximumCapacity;
        }
        if (internalDimensions != null) {
            this.internalDimensions = internalDimensions;
        }
        if (type != null) {
            this.type = type;
        }
        if (status != null) {
            this.status = status;
        }
        if (currentMileage != null) {
            this.currentMileage = currentMileage;
        }
        if (details != null) {
            this.details = details;
        }
        if (fleet != null) {
            this.fleet = fleet;
        }
        this.model = model;
    }
}
