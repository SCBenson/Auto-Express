package com.ct5221.auto_express.domain;
import jakarta.persistence.*;

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private long id;

    private String make;

    private String model;

    private int year;

    private double price;

    public Vehicle() {}

    public Vehicle(String make, String model, int year, double price){
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public long getId() {return id; }
    public void setId(long id) { this.id = id; }
    public String getMake() {return make;}
    public void setMake(String make) { this.make = make; }
    public String getModel() {return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() {return year; }
    public void setYear(int year) {this.year = year; }
    public double getPrice() { return price; }
    public void setPrice(double price) {this.price = price;}
}
