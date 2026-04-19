package org.cfs.bms2.repository;


import org.cfs.bms2.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by user
    List<Booking> findByUserId(Long userId);

    // Find bookings by show
    List<Booking> findByShowId(Long showId);

    // Find all booked seat IDs for a show
    @Query("SELECT s.id FROM Booking b JOIN b.seats s " +
            "WHERE b.show.id = :showId AND b.status = 'CONFIRMED'")
    List<Long> findBookedSeatIdsByShowId(@Param("showId") Long showId);
}