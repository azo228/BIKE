package com.bike.rental.audit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;
    private String name;
    private String status; // "available", "unavailable", "in_use"
    private String location;

    public Vehicle() {}

    public Vehicle(String name, String status, String location) {
        this.name = name;
        this.status = status;
        this.location = location;
    }




    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
}
