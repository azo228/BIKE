package com.bike.rental.audit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bike.rental.audit.model.Booking;
import com.bike.rental.audit.service.BookingService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }




    @PostMapping
    public ResponseEntity<EntityModel<Booking>> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.created(
                        linkTo(methodOn(BookingController.class).getBookingById(createdBooking.getId())).toUri())
                .body(EntityModel.of(createdBooking));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<EntityModel<Booking>> completeBooking(@PathVariable String id) {
        Booking completedBooking = bookingService.completeBooking(id);
        return ResponseEntity.ok(EntityModel.of(completedBooking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public CollectionModel<EntityModel<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<EntityModel<Booking>> bookingModels = bookings.stream()
                .map(booking -> EntityModel.of(booking,
                        linkTo(methodOn(BookingController.class).getBookingById(booking.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(bookingModels,
                linkTo(methodOn(BookingController.class).getAllBookings()).withSelfRel());
    }


    @GetMapping("/{id}")
    public EntityModel<Booking> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return EntityModel.of(booking,
                linkTo(methodOn(BookingController.class).getBookingById(id)).withSelfRel(),
                linkTo(methodOn(BookingController.class).getAllBookings()).withRel("bookings"));
    }
}
