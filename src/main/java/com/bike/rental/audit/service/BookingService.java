package com.bike.rental.audit.service;

import org.springframework.stereotype.Service;
import com.bike.rental.audit.model.Booking;
import com.bike.rental.audit.model.Vehicle;
import com.bike.rental.audit.repository.BookingRepository;
import com.bike.rental.audit.service.VehicleService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleService vehicleService;

    public BookingService(BookingRepository bookingRepository, VehicleService vehicleService) {
        this.bookingRepository = bookingRepository;
        this.vehicleService = vehicleService;
    }



    public Booking createBooking(Booking booking) {
        Vehicle vehicle = vehicleService.getVehicleById(booking.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!"available".equals(vehicle.getStatus())) {
            throw new RuntimeException("Vehicle not available");
        }

        vehicle.setStatus("in_use");
        vehicleService.updateVehicle(vehicle.getId(), vehicle);

        booking.setStatus("active");
        booking.setStartTime(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Booking completeBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("completed");
        booking.setEndTime(LocalDateTime.now());
        bookingRepository.save(booking);

        Vehicle vehicle = vehicleService.getVehicleById(booking.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setStatus("available");
        vehicleService.updateVehicle(vehicle.getId(), vehicle);

        return booking;
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

}
