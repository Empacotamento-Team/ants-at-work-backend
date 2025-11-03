package com.empacoters.antsback.logistics.domain.model;

import java.util.List;

public class Load {
    private final Long id;
    private Long shipmentId;
    private Truck relatedTruck;
    private List<Package> packages;

    public Load(Long id, Long shipmentId, Truck relatedTruck, List<Package> packages) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.relatedTruck = validateTruck(relatedTruck);
        this.packages = validatePackages(packages);
    }

    public Long id() {
        return id;
    }

    public Long shipmentId() {
        return shipmentId;
    }

    public void changeShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Truck relatedTruck() {
        return relatedTruck;
    }

    public void changeRelatedTruck(Truck relatedTruck) {
        this.relatedTruck = validateTruck(relatedTruck);
    }

    public List<Package> packages() {
        return packages;
    }

    public void changePackages(List<Package> packages) {
        this.packages = validatePackages(packages);
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
