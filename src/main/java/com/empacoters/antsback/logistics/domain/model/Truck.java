package com.empacoters.antsback.logistics.domain.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Truck{
    private final Long id;
    private String plate;
    private Integer maximumCapacity;
    private Dimensions internalDimensions;
    private Set<TruckType> types;
    private TruckStatus status;
    private LocalDate lastRevision;
    private Float currentMileage;
    private String details;
    private List<MaintenanceRecord> maintenanceHistory;

    public Truck(Long id, String plate, Integer maximumCapacity, Dimensions internalDimensions, Set<TruckType> types, TruckStatus status, LocalDate lastRevision, Float currentMileage, String details) {
        this.id = id;
        this.plate = plate;
        this.maximumCapacity = maximumCapacity;
        this.internalDimensions = internalDimensions;
        this.types = types;
        this.status = status;
        this.lastRevision = lastRevision;
        this.currentMileage = currentMileage;
        this.details = details;
        this.maintenanceHistory = new ArrayList<>();
    }

    public Long id() {return this.id;}

    public String plate() {return this.plate;}

    public Integer maximumCapacity(){ return this.maximumCapacity;}

    public Dimensions internalDimensions() {
        return this.internalDimensions;
    }

    public Set<TruckType> types() {return this.types;}

    public TruckStatus status(){return this.status;}

    public LocalDate lastRevision(){return this.lastRevision;}

    public Float currentMileage(){return this.currentMileage;}

    public String details(){return this.details;}

    public List<MaintenanceRecord> maintenanceHistory(){return this.maintenanceHistory;}

    public void changeTruckStatus(TruckStatus newStatus)
    {
        this.status = newStatus;
    }
    public void changeLastRevision(LocalDate newDate)
    {
        this.lastRevision = newDate;
    }
    public void addMaintenceRecord(MaintenanceRecord record)
    {
        maintenanceHistory.add(record);
    }
    public void update(
            Optional<String> plate,
            Optional<Integer> maximumCapacity,
            Optional<Dimensions> internalDimensions,
            Optional<Set<TruckType>> types,
            Optional<TruckStatus> status,
            Optional<Float> currentMileage,
            Optional<String> details
    ) {
        if (plate.isPresent()) {
            this.plate = plate.get();
        }
        if (maximumCapacity.isPresent()) {
            this.maximumCapacity = maximumCapacity.get();
        }
        if (internalDimensions.isPresent()) {
            this.internalDimensions = internalDimensions.get();
        }
        if (types.isPresent()) {
            this.types = types.get();
        }
        if (status.isPresent()) {
            this.status = status.get();
        }
        if (currentMileage.isPresent()) {
            this.currentMileage = currentMileage.get();
        }
        if (details.isPresent()) {
            this.details = details.get();
        }
    }
}
