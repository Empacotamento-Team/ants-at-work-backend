package com.empacoters.antsback.logistics.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fleet {
    private Long id;
    private String name;
    private String code;
    private String placeOfOperation;
    private List<Truck> trucks;

    public Fleet(Long id, String nome, String code, String placeOfOperation, List<Truck> trucks) {
        this.id = id;
        this.name = nome;
        this.code = code;
        this.placeOfOperation = placeOfOperation;
        this.trucks = new ArrayList<>(trucks.stream().filter(Objects::nonNull).toList());
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
    public void changeName(String nome) {
        this.name = nome;
    }

    public String codigo() {
        return code;
    }
    public void changeCode(String code) {
        this.code = code;
    }

    public String placeOfOperation() {
        return placeOfOperation;
    }
    public void changePlaceOfOperation(String placeOfOperation) {
        this.placeOfOperation = placeOfOperation;
    }

    public List<Truck> listTrucks() {
        return this.trucks;
    }

    public void addTruck(Truck truck) {
        this.trucks.add(truck);
        truck.changeFleet(this);
    }

    public Integer trucksCount() {
        return this.trucks.size();
    }

    public Integer activeTrucksCount() {
        int activeCount = 0;
        for (Truck truck : trucks) {
            if (truck.status() == TruckStatus.AVAILABLE)
                activeCount += 1;
        }
        return activeCount;
    }

    public Integer underMaintenanceTrucksCount() {
        int underMaintenanceCount = 0;
        for (Truck truck : trucks) {
            if (truck.status() == TruckStatus.UNDER_MAINTENANCE)
                underMaintenanceCount += 1;
        }
        return underMaintenanceCount;
    }

    public double calculateAverageCapacity() {
        if (trucksCount() == 0)
            return 0;

        double capacitySum = 0;
        for (Truck truck : trucks) {
            capacitySum += truck.maximumCapacity();
        }

        return capacitySum / trucks.size();
    }
}
