package com.ct5221.auto_express.dto;

public class VehicleDTO {

    private Long id;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private Double price;
    private Double mileage;
    private Long dealerId;

    public VehicleDTO() {}

    public VehicleDTO(String make, String model, String color, Integer year, Double price, Double mileage, Long dealerId) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.dealerId = dealerId;
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

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getMileage() { return mileage; }
    public void setMileage(Double mileage) {this.mileage = mileage;}

    public Long getDealerId() { return dealerId; }
    public void setDealerId(Long dealerId) { this.dealerId = dealerId;}
}
