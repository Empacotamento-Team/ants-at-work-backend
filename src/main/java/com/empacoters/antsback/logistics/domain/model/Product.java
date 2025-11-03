package com.empacoters.antsback.logistics.domain.model;

public class Product {
    private final Long id;
    private String name;
    private ProductFamily family;
    private Dimensions dimensions;
    private Double weight;
    private Double maxSupportedWeight;
    private String batch;
    private boolean fragile;

    public Product(Long id, String name, ProductFamily family, Dimensions dimensions, Double weight, Double maxSupportedWeight, String batch, boolean fragile) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.dimensions = dimensions;
        this.weight = weight;
        this.maxSupportedWeight = maxSupportedWeight;
        this.batch = batch;
        this.fragile = fragile;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void changeName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("O nome do produto não pode ser nulo e precisa ter 3 caracteres ou mais.");
        this.name = name;
    }

    public ProductFamily family() {
        return family;
    }

    public void changeFamily(ProductFamily family) {
        if (family == null)
            throw new IllegalArgumentException("A família não deve ser nula.");
        this.family = family;
    }

    public Dimensions dimensions() {
        return dimensions;
    }

    public void changeDimensions(Dimensions dimensions) {
        if (dimensions == null)
            throw new IllegalArgumentException("As dimensões do produto não podem ser nulas.");
        this.dimensions = dimensions;
    }

    public Double weight() {
        return weight;
    }

    public void changeWeight(Double weight) {
        if (weight == null || weight < 0)
            throw new IllegalArgumentException("O peso do produto não pode ser nulo ou menor que zero.");
        this.weight = weight;
    }

    public Double maxSupportedWeight() {
        return maxSupportedWeight;
    }

    public void changeMaxSupportedWeight(Double maxSupportedWeight) {
        if (maxSupportedWeight < 0)
            throw new IllegalArgumentException("A capacidade máxima de peso do produto não pode ser menor que zero.");
        this.maxSupportedWeight = maxSupportedWeight;
    }

    public String batch() {
        return batch;
    }

    public void changeBatch(String batch) {
        this.batch = batch;
    }

    public boolean fragile() {
        return fragile;
    }

    public void changeFragile(boolean fragile) {
        this.fragile = fragile;
    }
}
