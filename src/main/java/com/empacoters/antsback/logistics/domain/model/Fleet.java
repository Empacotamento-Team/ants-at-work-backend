package com.empacoters.antsback.logistics.domain.model;

import java.util.List;

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

    public void changeListOfTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
