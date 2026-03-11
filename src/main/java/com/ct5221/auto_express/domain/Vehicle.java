package com.ct5221.auto_express.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Make cannot be null")
    private String make;
    @NotNull(message = "Model cannot be null")
    private String model;
    @NotNull(message = "Color cannot be null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Color must contain only letters")
    private String color;
    @Min(value = 1900, message= "Year must be no earlier than 1900")
    @Max(value = 2026, message= "Year must be no later than 2026")
    private Integer year;
    @Min(value=0, message= "Mileage must be positive")
    private Double mileage;
    @Min(value=0, message= "Price must be negative")
    private Double price;

    public Vehicle() {}

    public Vehicle(String make, String model, String color, Integer year, Double mileage, Double price){
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    public long getId() {return id; }
    public void setId(long id) { this.id = id; }
    public String getMake() {return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() {return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getYear() {return year; }
    public void setYear(int year) {this.year = year; }
    public Double getMileage() {return mileage; }
    public void setMileage(Double mileage) { this.mileage = mileage; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) {this.price = price; }
    public String getColor() { return color;}
    public void setColor(String color) { this.color = color;}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer; // A vehicle belongs to one dealer.

    public Dealer getDealer(){
        return dealer;
    }

    public void setDealer(Dealer dealer){
        this.dealer = dealer;
    }


}
