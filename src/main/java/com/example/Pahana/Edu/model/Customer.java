package com.example.Pahana.Edu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String contactNo;
    private String address;
   

    // Default constructor
    public Customer() {}

    // Full constructor
    public Customer(String username, String password, String name, String contactNo, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
    }

    // Getters + Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

}