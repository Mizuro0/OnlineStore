package org.mizuro.onlinestore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "air_flights")
public class AirFlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_airport_id", referencedColumnName = "id")
    private AirportEntity origin;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", referencedColumnName = "id")
    private AirportEntity destination;

    @OneToMany(mappedBy = "airFlight", fetch = FetchType.LAZY)
    private List<UserEntity> passengers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "departure_date")
    private Date departureDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @Column(name = "price")
    private int price;

    @Column(name = "trip_class")
    private int tripClass;

    @Column(name = "duration")
    private int duration;

    @Column(name = "distance")
    private int distance;


}
