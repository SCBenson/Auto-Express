package com.ct5221.auto_express.domain;

import com.ct5221.auto_express.security.Authenticatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")

public class User implements Authenticatable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
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
    @NotNull(message = "Phone number cannot be null")
    @Size(min = 10, max=10, message = "Phone number must be 10 digits")
    private String phone;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    private String location;

    public User(){}

    public User(String firstName, String lastName, String username, String email, Integer age, String phone, String password, String location){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.location = location;
    }

    public Long getId() {return id; }
    public void setId(Long id) {this.id = id; }

    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() {return firstName; }
    public void setFirstName(String firstName) {this.firstName = firstName; }

    public String getLastName() {return lastName; }
    public void setLastName(String lastName) {this.lastName = lastName; }

    public String getEmail() {return email; }
    public void setEmail(String email) {this.email = email; }

    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age; }

    public String getPhone() {return phone; }
    public void setPhone(String phone) {this.phone = phone; }

    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }

    public String getLocation() {return location; }
    public void setLocation(String location) {this.location = location; }

    @Override
    public String getRole(){
        return "USER";
    }

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Vehicle> purchasedVehicles = new ArrayList<>();

    public List<Vehicle> getPurchasedVehicles(){
        return purchasedVehicles;
    }

    public void setPurchasedVehicles(List<Vehicle> purchasedVehicles){
        this.purchasedVehicles = purchasedVehicles;
    }

}
