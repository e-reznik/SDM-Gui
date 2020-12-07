package com.mycompany.dto;

import com.mycompany.sdm.interfaces.IProperties;

public class Product {

    private IProperties.ProductTypes type;
    private String title;
    private int quality;
    private int bestBefore;
    private double price;
    private boolean disposable;
    private Long id;

    public Product(IProperties.ProductTypes type, String title, int quality, int bestBefore, double price, boolean disposable, Long id) {
        this.type = type;
        this.title = title;
        this.quality = quality;
        this.bestBefore = bestBefore;
        this.price = price;
        this.disposable = disposable;
        this.id = id;
    }

    public IProperties.ProductTypes getType() {
        return type;
    }

    public void setType(IProperties.ProductTypes type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(int bestBefore) {
        this.bestBefore = bestBefore;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public void setDisposable(boolean disposable) {
        this.disposable = disposable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
