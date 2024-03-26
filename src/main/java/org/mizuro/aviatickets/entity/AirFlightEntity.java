package org.mizuro.aviatickets.entity;

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
@Entity
@Table(name = "air_flights")
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

    @OneToMany(mappedBy = "airFlight", fetch = FetchType.EAGER)
    private List<UserEntity> passengers;

    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @Column(name = "price")
    private int value;

    @Column(name = "trip_class")
    private int tripClass;

    @Column(name = "duration")
    private int duration;

    @Column(name = "distance")
    private int distance;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "origin = " + origin + ", " +
                "destination = " + destination + ", " +
                "departureDate = " + departureDate + ", " +
                "returnDate = " + returnDate + ", " +
                "price = " + value + ", " +
                "tripClass = " + tripClass + ", " +
                "duration = " + duration + ", " +
                "distance = " + distance + ")";
    }
    private boolean actual;
}
