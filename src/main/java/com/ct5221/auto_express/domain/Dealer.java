package com.ct5221.auto_express.domain;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dealers")

public class Dealer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> inventory = new ArrayList<>();

    public List<Vehicle> getInventory(){
        return inventory;
    }

    public void setInventory(List<Vehicle> inventory){
        this.inventory = inventory;
    }


    public Dealer(){}

    public Dealer(String username, String email, String phone, String password){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {return id; }
    public void setId(Long id) {this.id = id; }

    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() {return email; }
    public void setEmail(String email) {this.email = email; }

    public String getPhone() {return phone; }
    public void setPhone(String phone) {this.phone = phone; }

    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }

}
