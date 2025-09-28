package com.empacoters.antsback.logistics.domain.model;


import java.util.Set;

public class Truck {
    private final Long id;
    private final String plate;
    private final Integer maximumCapacity;
    private final Float internalVolume;
    private final Set<TruckType> types;
    private final TruckStatus status;
    private final String lastRevision;
    private final Float currentMileage;

    public Truck(Long id, String plate, Integer maximumCapacity, Float internalVolume, Set<TruckType> types, TruckStatus status, String lastRevision, Float currentMileage) {
        this.id = id;
        this.plate = plate;
        this.maximumCapacity = maximumCapacity;
        this.internalVolume = internalVolume;
        this.types = types;
        this.status = status;
        this.lastRevision = lastRevision;
        this.currentMileage = currentMileage;
    }

    public Long id() {return this.id;}

    public String plate() {return this.plate;}

    public Integer maximumCapacity(){ return  this.maximumCapacity;}

    public Float internalVolume(){return this.internalVolume;}

    public Set<TruckType> types() {return this.types;}

    public TruckStatus status(){return this.status;}

    public String lastRevision(){return this.lastRevision;}

    public Float curreantMileage(){return this.currentMileage;}
}
