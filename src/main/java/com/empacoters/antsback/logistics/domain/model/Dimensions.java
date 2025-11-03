package com.empacoters.antsback.logistics.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.ValidationException;

@Embeddable
public class Dimensions {
    private Double height;
    private Double width;
    private Double length;

    public Dimensions(Double height, Double width, Double length) {
        this.height = validateHeight(height);
        this.width = validateWidth(width);
        this.length = validateLength(length);
    }

    public Dimensions() {
        height = 0.0;
        width = 0.0;
        length = 0.0;
    }

    // Height
    public Double height() {
        return height;
    }
    public void changeHeight(Double height)
    {
        this.height = validateHeight(height);
    }

    // Width
    public Double width() {
        return width;
    }
    public void changeWidth(Double width)
    {
        this.width = validateWidth(width);
    }

    // Length
    public Double length() {
        return length;
    }
    public void changeLength(Double length)
    {
        this.length = validateLength(length);
    }

    private Double validateHeight(Double value) {
        if (height == null || height < 0)
            throw new ValidationException("A altura não pode ser nula ou menor que zero.");
        return value;
    }

    private Double validateWidth(Double value) {
        if (width == null || width < 0)
            throw new ValidationException("A largura não pode ser nula ou menor que zero.");
        return value;
    }

    private Double validateLength(Double value) {
        if (length == null || length < 0)
            throw new ValidationException("O comprimento não pode ser nulo ou menor que zero.");
        return value;
    }
}
