package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "tickets")
@AllArgsConstructor
@Getter
@Entity
@Setter
@NoArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_airport_id", referencedColumnName = "id")
    private AirportEntity from;

    @ManyToOne
    @JoinColumn(name = "to_airport_id", referencedColumnName = "id")
    private AirportEntity to;

    @Column(name = "date_to_issue")
    private String dateToIssue;

    @Column(name = "actual")
    private boolean actual;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;

    @Column(name = "seat")
    private String seat;

    @Column(name = "price")
    private int price;

    @Column(name = "etkt")
    private String etkt;


    @Column(name = "race_number")
    private String raceNumber;

    @Column(name = "return_transfers")
    private int returnTransfers;

    @Column(name = "transfers", nullable = false)
    private int transfers;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "duration_back")
    private int durationBack;

    @Column(name = "duration_to", nullable = false)
    private int durationTo;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "departure_date", nullable = false)
    private String departureDate;

}
