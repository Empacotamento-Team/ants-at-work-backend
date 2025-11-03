package com.empacoters.antsback.logistics.domain.model;

public class ProductFamily {
    private final Long id;
    private String name;
    private String description;
    private Double defaultMaxSupportedWeight;

    public ProductFamily(Long id, String name, String description, Double defaultMaxSupportedWeight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultMaxSupportedWeight = defaultMaxSupportedWeight;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void changeName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("O nome da família não pode ser nulo e precisa ter 3 caracteres ou mais.");
        this.name = name;
    }

    public String description() {
        return description;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public Double defaultMaxSupportedWeight() {
        return defaultMaxSupportedWeight;
    }

    public void changeDefaultMaxSupportedWeight(Double defaultMaxSupportedWeight) {
        if (defaultMaxSupportedWeight == null || defaultMaxSupportedWeight < 0)
            throw new IllegalArgumentException("A capacidade máxima padrão da família não pode ser negativa/nula.");
        this.defaultMaxSupportedWeight = defaultMaxSupportedWeight;
    }
}
