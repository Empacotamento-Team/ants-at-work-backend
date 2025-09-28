package com.empacoters.antsback.logistics.domain.model;


import com.sun.tools.javac.Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Truck{
    private final Long id;
    private  String plate;
    private  Integer maximumCapacity;
    private  Float internalVolume;
    private  Set<TruckType> types;
    private TruckStatus status;
    private LocalDate lastRevision;
    private Float currentMileage;
    private String details;
    private List<MaintenanceRecord> maintenanceHistory;

    public Truck(Long id, String plate, Integer maximumCapacity, Float internalVolume, Set<TruckType> types, TruckStatus status, LocalDate lastRevision, Float currentMileage, String details) {
        this.id = id;
        this.plate = plate;
        this.maximumCapacity = maximumCapacity;
        this.internalVolume = internalVolume;
        this.types = types;
        this.status = status;
        this.lastRevision = lastRevision;
        this.currentMileage = currentMileage;
        this.details = details;
        this.maintenanceHistory = new ArrayList<>();
    }

    public Long id() {return this.id;}

    public String plate() {return this.plate;}

    public Integer maximumCapacity(){ return  this.maximumCapacity;}

    public Float internalVolume(){return this.internalVolume;}

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
            String plate,
            Integer maximumCapacity,
            Float internalVolume,
            Set<TruckType> types,
            TruckStatus status,
            Float currentMileage,
            String details
    ) {
        if (plate != null) this.plate = plate;
        if (maximumCapacity != null) this.maximumCapacity = maximumCapacity;
        if (internalVolume != null) this.internalVolume = internalVolume;
        if (types != null) this.types = types;
        if (status != null) this.status = status;
        if (currentMileage != null) this.currentMileage = currentMileage;
        if (details != null) this.details = details;
    }
}
