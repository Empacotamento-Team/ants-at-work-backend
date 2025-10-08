package com.empacoters.antsback.logistics.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Dimensions {
    private Double height;
    private Double width;
    private Double length;

    public Dimensions(Double height, Double width, Double length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public Dimensions() {}

    // Height
    public Double height() {
        return height;
    }
    public void changeHeight(Double height) {
        this.height = height;
    }

    // Width
    public Double width() {
        return width;
    }
    public void changeWidth(Double width) {
        this.width = width;
    }

    // Length
    public Double length() {
        return length;
    }
    public void changeLength(Double length) {
        this.length = length;
    }
}
