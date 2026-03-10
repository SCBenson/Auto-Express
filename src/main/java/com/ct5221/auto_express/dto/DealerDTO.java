package com.ct5221.auto_express.dto;

import java.util.List;

public class DealerDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String phone;
    private String password;
    private List<Long> vehicleIds;

    public DealerDTO(){}

    public DealerDTO(String username, String firstName, String lastName, String email, Integer age, String phone, String password){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getUsername(){return username;}
    public void setUsername(String username) { this.username = username;}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Long> getVehicleIds() { return vehicleIds; }
    public void setVehicleIds(List<Long> vehicleIds) { this.vehicleIds = vehicleIds;}
}
