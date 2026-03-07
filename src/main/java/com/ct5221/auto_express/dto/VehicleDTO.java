package com.ct5221.auto_express.dto;

public class VehicleDTO {

    private Long id;
    private String make;
    private String model;
    private int year;
    private Double price;
    private Long dealerId;

    public VehicleDTO() {}

    public VehicleDTO(String make, String model, int year, Double price, Long dealerId) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.dealerId = dealerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Long getDealerId() { return dealerId; }
    public void setDealerId(Long dealerId) { this.dealerId = dealerId;}
}
