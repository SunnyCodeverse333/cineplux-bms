package org.cfs.bms2.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatres")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false )
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false) // a column "city_id" will be created in that City reference in database it
    // stores
    // city id ) is
    // stored
    private City city; //  this city(object) reference is gettign shared among all the threatres
}

