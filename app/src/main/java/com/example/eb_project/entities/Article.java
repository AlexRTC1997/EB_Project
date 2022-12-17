package com.example.eb_project.entities;

public class Article {
    private int id;
    private String name;
    private int measurement_unit;
    private double price;
    private int brand;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeasurement_unit() {
        return measurement_unit;
    }

    public void setMeasurement_unit(int measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
