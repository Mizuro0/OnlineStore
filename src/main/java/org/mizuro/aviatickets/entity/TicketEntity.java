package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

    @Column(name = "etkt")
    private String etkt;

    @Column(name = "booking_status")
    private int bookingStatus;

    @Column(name = "time_date")
    private String timeDate;
}
