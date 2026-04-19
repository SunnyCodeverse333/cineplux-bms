package org.cfs.bms2.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.BookingRequest;
import org.cfs.bms2.entity.Booking;
import org.cfs.bms2.entity.Seat;
import org.cfs.bms2.entity.Show;
import org.cfs.bms2.entity.User;
import org.cfs.bms2.enums.BookingStatus;
import org.cfs.bms2.enums.SeatType;
import org.cfs.bms2.repository.BookingRepository;
import org.cfs.bms2.repository.SeatRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ShowService showService;
    private final SeatRepository seatRepository;

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest){

        //  Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.getUserRepository().findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  Get show
        Show show = showService.getShowById(bookingRequest.getShowId());

        //  Check already booked seats
        List<Long> alreadyBookedSeats = bookingRepository.findBookedSeatIdsByShowId(show.getId());

        for(Long seatId: bookingRequest.getSeatIds()){
            if(alreadyBookedSeats.contains(seatId)){
                throw new RuntimeException("Seat with id " + seatId + " is already booked");
            }
        }

        //  Fetch seats
        List<Seat> seats = seatRepository.findAllById(bookingRequest.getSeatIds());

        if(seats.size() != bookingRequest.getSeatIds().size()){
            throw new RuntimeException("Some seats are invalid");
        }

        //  Calculate price
        double totalPrice = 0;

        for (Seat seat : seats) {
            if (seat.getSeatType() == SeatType.PREMIUM) {
                totalPrice += show.getTicketPrice() * 1.5;
            } else {
                totalPrice += show.getTicketPrice();
            }
        }

        //  Create booking
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seats(seats)
                .totalPrice(totalPrice)
                .build();

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Booking Id "+ id + " doesn't exist...")
        );
    }
    public List<Booking> getBookingByUserId(Long userId){
        return bookingRepository.findByUserId(userId);
    }
    @Transactional
    public Booking cancelBooking(Long bookingId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Booking booking = getBookingById(bookingId);

        if(!booking.getUser().getUsername().equals(username)){
            throw new RuntimeException("Unauthorized");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
    public List<Seat> getAvailableSeats(Long showId){
        Show show = showService.getShowById(showId);
        List<Seat> allSeats= seatRepository.findByScreen_Id(show.getScreen().getId());
        List<Long>  bookingSeatIds = bookingRepository.findBookedSeatIdsByShowId(showId);
        return allSeats.stream()
                .filter(seat-> !bookingSeatIds.contains(seat.getId()))
                .toList();
    }

}
