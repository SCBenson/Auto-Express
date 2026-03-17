package com.ct5221.auto_express.dto;

import java.math.BigDecimal;

public class VehicleDTO {

    private Long id;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private BigDecimal price;
    private Double mileage;
    private Long dealerId;
    private boolean available = true;

    public VehicleDTO() {}

    public VehicleDTO(String make, String model, String color, Integer year, BigDecimal price, Double mileage, Long dealerId, boolean available) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.dealerId = dealerId;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Double getMileage() { return mileage; }
    public void setMileage(Double mileage) {this.mileage = mileage;}

    public Long getDealerId() { return dealerId; }
    public void setDealerId(Long dealerId) { this.dealerId = dealerId;}

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
