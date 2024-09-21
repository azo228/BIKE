package com.bike.rental.audit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String vehicleId;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // "active", "completed", "cancelled"

    public Booking() {}

    public Booking(String vehicleId, String userId, LocalDateTime startTime, String status) {
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.startTime = startTime;
        this.status = status;
    }



    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
