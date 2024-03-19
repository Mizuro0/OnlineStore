package org.mizuro.onlinestore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "airports")
public class AirportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "iata_code")
    private short iataCode;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "origin", fetch = FetchType.LAZY)
    private List<AirFlightEntity> airFlights;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private List<AirFlightEntity> airFlightDestinations;

}
