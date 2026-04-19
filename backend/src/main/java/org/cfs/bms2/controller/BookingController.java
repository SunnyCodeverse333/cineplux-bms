package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.BookingRequest;
import org.cfs.bms2.entity.Booking;
import org.cfs.bms2.entity.Seat;
import org.cfs.bms2.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request){
//        System.out.println("DEBUG → userId: " + request.getUserId());
//        System.out.println("DEBUG → showId: " + request.getShowId());
//        System.out.println("DEBUG → seatIds: " + request.getSeatIds());
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(bookingService.getBookingByUserId(userId));
    }
    @GetMapping("/show/{showId}/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(bookingService.getAvailableSeats(showId));
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }



}
