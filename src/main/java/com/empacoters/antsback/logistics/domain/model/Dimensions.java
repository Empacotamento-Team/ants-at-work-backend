package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("height")
    public Double height() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public void changeHeight(Double height)
    {
        this.height = validateHeight(height);
    }

    // Width
    @JsonProperty("width")
    public Double width() {
        return width;
    }
    
    public void setWidth(Double width) {
        this.width = width;
    }
    
    public void changeWidth(Double width)
    {
        this.width = validateWidth(width);
    }

    // Length
    @JsonProperty("length")
    public Double length() {
        return length;
    }
    
    public void setLength(Double length) {
        this.length = length;
    }
    
    public void changeLength(Double length)
    {
        this.length = validateLength(length);
    }

    private Double validateHeight(Double value) {
        if (value == null || value <= 0)
            throw new ValidationException("A altura deve ser maior que 0.");
        return value;
    }

    private Double validateWidth(Double value) {
        if (value == null || value <= 0)
            throw new ValidationException("A largura deve ser maior que 0.");
        return value;
    }

    private Double validateLength(Double value) {
        if (value == null || value <= 0)
            throw new ValidationException("O comprimento deve ser maior que 0.");
        return value;
    }
}
