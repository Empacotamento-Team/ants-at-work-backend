package com.empacoters.antsback.logistics.domain.model;


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

    public Long id() {
        return id;
    }

    public Long loadId() {
        return loadId;
    }

    public void changeLoadId(Long loadId) {
        this.loadId = loadId;
    }

    public Packaging packaging() {
        return packaging;
    }

    public void changePackaging(Packaging packaging) {
        if (packaging == null)
            throw new IllegalArgumentException("A embalagem não pode ser nula");
        this.packaging = packaging;
    }

    public Product product() {
        return product;
    }

    public void changeProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("O produto não pode ser nulo");
        this.product = product;
    }

    public Double supportedWeight() {
        return supportedWeight;
    }

    public void changeSupportedWeight(Double supportedWeight) {
        if (supportedWeight == null || supportedWeight < 0)
            throw new IllegalArgumentException("O peso suportado não pode ser nulo ou menor que zero.");
        this.supportedWeight = supportedWeight;
    }

    public Double xPosition() {
        return xPosition;
    }

    public void changeXPosition(Double xPosition) {
        this.xPosition = xPosition;
    }

    public Double yPosition() {
        return yPosition;
    }

    public void changeYPosition(Double yPosition) {
        this.yPosition = yPosition;
    }

    public Double zPosition() {
        return zPosition;
    }

    public void changeZPosition(Double zPosition) {
        this.zPosition = zPosition;
    }

    public String orientation() {
        return orientation;
    }

    public void changeOrientation(String orientation) {
        this.orientation = orientation;
    }
}
