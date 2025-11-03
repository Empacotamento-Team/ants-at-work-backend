package com.empacoters.antsback.logistics.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private final Long id;
    private Packaging packaging;
    private List<Product> products;
    private Double supportedWeight;

    public Package(Long id, Packaging packaging, List<Product> products, Double supportedWeight) {
        this.id = id;
        this.packaging = packaging;
        this.products = products != null ? products : new ArrayList<>();
        this.supportedWeight = supportedWeight;
    }

    public Long id() {
        return id;
    }

    public Packaging packaging() {
        return packaging;
    }

    public void changePackaging(Packaging packaging) {
        if (packaging == null)
            throw new IllegalArgumentException("A embalagem não pode ser nula");
        this.packaging = packaging;
    }

    public List<Product> products() {
        return products;
    }

    public void changeProducts(List<Product> products) {
        if (products == null)
            throw new IllegalArgumentException("A lista de produtos não pode ser nula");
        this.products = products;
    }

    public Double supportedWeight() {
        return supportedWeight;
    }

    public void changeSupportedWeight(Double supportedWeight) {
        if (supportedWeight == null || supportedWeight < 0)
            throw new IllegalArgumentException("O peso suportado não pode ser nulo ou menor que zero.");
        this.supportedWeight = supportedWeight;
    }
}
