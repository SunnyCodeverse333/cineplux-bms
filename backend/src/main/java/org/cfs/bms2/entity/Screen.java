package org.cfs.bms2.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="screens")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String name; // AUDE -I AUDI-2

    private Integer totalSeats;

    @ManyToOne
    @JoinColumn(name = "theater_id" , nullable = false)
    private Theater theater;

}
