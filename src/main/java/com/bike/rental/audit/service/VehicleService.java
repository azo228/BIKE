package com.bike.rental.audit.service;

import org.springframework.stereotype.Service;
import com.bike.rental.audit.model.Vehicle;
import com.bike.rental.audit.repository.VehicleRepository;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }



    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String id, Vehicle vehicleDetails) {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setName(vehicleDetails.getName());
                    vehicle.setStatus(vehicleDetails.getStatus());
                    vehicle.setLocation(vehicleDetails.getLocation());
                    return vehicleRepository.save(vehicle);
                })
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }
}
