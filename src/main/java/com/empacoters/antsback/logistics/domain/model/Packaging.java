package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Packaging {
    private final Long id;
    private String name;
    private String description;
    private Dimensions internalDimensions;

    public Packaging(Long id, String name, String description, Dimensions internalDimensions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.internalDimensions = internalDimensions;
    }

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("name")
    public String name() {
        return name;
    }
    public void changeName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("O nome da embalagem não pode ser nulo e precisa ter 3 caracteres ou mais.");
        this.name = name;
    }

    @JsonProperty("description")
    public String description() {
        return description;
    }
    public void changeDescription(String description) {
        this.description = description;
    }

    @JsonProperty("internalDimensions")
    public Dimensions internalDimensions() {
        return internalDimensions;
    }
    public void changeInternalDimensions(Dimensions internalDimensions) {
        if (internalDimensions == null)
            throw new IllegalArgumentException("As dimensões internas não podem ser nulas");
        this.internalDimensions = internalDimensions;
    }
}
