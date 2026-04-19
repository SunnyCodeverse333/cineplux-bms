package org.cfs.bms2.entity;


import jakarta.persistence.*;
import lombok.*;
import org.cfs.bms2.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id" , nullable = false)
    private Show show;

    @ManyToMany
    @JoinTable(name = "booking_seats" ,
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private LocalDateTime bookedAt;

    // run this before saving in the Database
    @PrePersist
    private void onCreate(){

        this.bookedAt = LocalDateTime.now();
        if(this.status ==null){
            this.status = BookingStatus.CONFIRMED;
        }
    }
}
