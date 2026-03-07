package com.ct5221.auto_express.dto;

import java.util.List;

public class DealerDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private List<Long> vehicleIds;

    public DealerDTO(){}

    public DealerDTO(String username, String email, String phone, String password){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getUsername(){return username;}
    public void setUsername(String username) { this.username = username;}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Long> getVehicleIds() { return vehicleIds; }
    public void setVehicleIds(List<Long> vehicleIds) { this.vehicleIds = vehicleIds;}
}
