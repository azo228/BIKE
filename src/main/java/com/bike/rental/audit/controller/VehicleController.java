package com.bike.rental.audit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bike.rental.audit.model.Vehicle;
import com.bike.rental.audit.service.VehicleService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Vehicle>> updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(EntityModel.of(updatedVehicle));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public CollectionModel<EntityModel<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<EntityModel<Vehicle>> vehicleModels = vehicles.stream()
                .map(vehicle -> EntityModel.of(vehicle,
                        linkTo(methodOn(VehicleController.class).getVehicleById(vehicle.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(vehicleModels,
                linkTo(methodOn(VehicleController.class).getAllVehicles()).withSelfRel());
    }


    @GetMapping("/{id}")
    public EntityModel<Vehicle> getVehicleById(@PathVariable String id) {
        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        return EntityModel.of(vehicle,
                linkTo(methodOn(VehicleController.class).getVehicleById(id)).withSelfRel(),
                linkTo(methodOn(VehicleController.class).getAllVehicles()).withRel("vehicles"));
    }

    // Создать новое транспортное средство
    @PostMapping
    public ResponseEntity<EntityModel<Vehicle>> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        return ResponseEntity.created(
                        linkTo(methodOn(VehicleController.class).getVehicleById(createdVehicle.getId())).toUri())
                .body(EntityModel.of(createdVehicle));
    }



}
