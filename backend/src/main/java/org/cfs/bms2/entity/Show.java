package org.cfs.bms2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shows")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Show {
//👉 Show = Movie + Screen + Time
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne
    @JoinColumn(name = "screen_id" ,nullable = false)
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;


    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime startTime;


    private LocalTime endTime;

    private Double ticketPrice;
}
