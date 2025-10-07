package com.empacoters.antsback.logistics.domain.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Truck{
    private final Long id;
    private String plate;
    private Integer maximumCapacity;
    private Dimensions internalDimensions;
    private TruckType type;
    private TruckStatus status;
    private LocalDate lastRevision;
    private Double currentMileage;
    private String details;
    private List<MaintenanceRecord> maintenanceHistory;
    private Fleet fleet;

    public Truck(Long id, String plate, Integer maximumCapacity, Dimensions internalDimensions, TruckType type, TruckStatus status, LocalDate lastRevision, Double currentMileage, String details, Fleet fleet) {
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
    }

    public Long id() {return this.id;}

    public String plate() {return this.plate;}

    public Integer maximumCapacity(){ return this.maximumCapacity;}

    public Dimensions internalDimensions() {
        return this.internalDimensions;
    }

    public TruckType type() {return this.type;}

    public TruckStatus status(){return this.status;}

    public LocalDate lastRevision(){return this.lastRevision;}

    public Double currentMileage(){return this.currentMileage;}

    public String details(){return this.details;}

    public List<MaintenanceRecord> maintenanceHistory(){return this.maintenanceHistory;}

    public Fleet fleet(){return this.fleet;}

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
    public void changeFleet(Fleet newFleet) {
        this.fleet = newFleet;
    }
    public void update(
            String plate,
            Integer maximumCapacity,
            Dimensions internalDimensions,
            TruckType type,
            TruckStatus status,
            Double currentMileage,
            String details,
            Fleet fleet
    ) {
        if (plate != null && !plate.isEmpty()) {
            this.plate = plate;
        }
        if (maximumCapacity != null && maximumCapacity > 0) {
            this.maximumCapacity = maximumCapacity;
        }
        if (internalDimensions != null) {
            this.internalDimensions = internalDimensions();
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
    }
}
