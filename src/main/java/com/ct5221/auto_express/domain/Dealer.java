package com.ct5221.auto_express.domain;
import com.ct5221.auto_express.security.Authenticatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dealers")

public class Dealer implements Authenticatable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @NotNull(message = "First Name cannot be null")
    @Size(min = 2, max = 100, message = "First Name must be between 2 and 100 characters")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 2, max = 100, message = "Last Name must be between 2 and 100 characters")
    private String lastName;
    @Column(nullable = false)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;
    @Size(min = 10, max=10, message = "Phone number must be 10 digits")
    private String phone;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    private String dealershipName;
    private String location;

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> inventory = new ArrayList<>(); //A dealer has many vehicles.

    public List<Vehicle> getInventory(){
        return inventory;
    }

    public void setInventory(List<Vehicle> inventory){
        this.inventory = inventory;
    }

    public Dealer(){}

    public Dealer(String firstName, String lastName, String username, String email, Integer age, String phone, String password, String dealershipName, String location){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.dealershipName = dealershipName;
        this.location = location;
    }

    public Long getId() {return id; }
    public void setId(Long id) {this.id = id; }

    public String getFirstName() {return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() {return email; }
    public void setEmail(String email) {this.email = email; }

    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age; }

    public String getPhone() {return phone; }
    public void setPhone(String phone) {this.phone = phone; }

    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }

    public String getDealershipName() {return dealershipName; }
    public void setDealershipName(String dealershipName) {this.dealershipName = dealershipName; }

    public String getLocation() {return location; }
    public void setLocation(String location) {this.location = location; }

    @Override
    public String getRole(){
        return "DEALER";
    }

}
