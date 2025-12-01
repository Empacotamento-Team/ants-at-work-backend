package com.empacoters.antsback.logistics.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Package {
    private final Long id;
    private Long loadId;
    private Packaging packaging;
    private Product product;
    private Double supportedWeight;
    private Double xPosition;
    private Double yPosition;
    private Double zPosition;
    private String orientation;

    public Package(Long id, Long loadId, Packaging packaging, Product product, Double supportedWeight, Double xPosition, Double yPosition, Double zPosition, String orientation) {
        this.id = id;
        this.loadId = loadId;
        this.packaging = packaging;
        this.product = product;
        this.supportedWeight = supportedWeight;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
        this.orientation = orientation;
    }

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("loadId")
    public Long loadId() {
        return loadId;
    }

    @JsonProperty("changeLoadId")
    public void changeLoadId(Long loadId) {
        this.loadId = loadId;
    }

    @JsonProperty("packaging")
    public Packaging packaging() {
        return packaging;
    }

    public void changePackaging(Packaging packaging) {
        if (packaging == null)
            throw new IllegalArgumentException("A embalagem não pode ser nula");
        this.packaging = packaging;
    }

    @JsonProperty("product")
    public Product product() {
        return product;
    }

    public void changeProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("O produto não pode ser nulo");
        this.product = product;
    }

    @JsonProperty("supportedWeight")
    public Double supportedWeight() {
        return supportedWeight;
    }

    public void changeSupportedWeight(Double supportedWeight) {
        if (supportedWeight == null || supportedWeight < 0)
            throw new IllegalArgumentException("O peso suportado não pode ser nulo ou menor que zero.");
        this.supportedWeight = supportedWeight;
    }

    @JsonProperty("xPosition")
    public Double xPosition() { return xPosition; }

    public void changeXPosition(Double xPosition) {
        this.xPosition = xPosition;
    }

    @JsonProperty("yPosition")
    public Double yPosition() {
        return yPosition;
    }

    public void changeYPosition(Double yPosition) {
        this.yPosition = yPosition;
    }

    @JsonProperty("zPosition")
    public Double zPosition() {
        return zPosition;
    }

    public void changeZPosition(Double zPosition) {
        this.zPosition = zPosition;
    }

    @JsonProperty("orientation")
    public String orientation() {
        return orientation;
    }

    public void changeOrientation(String orientation) {
        this.orientation = orientation;
    }
}
