package org.mizuro.aviatickets.utils;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.services.AirportService;
import org.mizuro.aviatickets.services.UserService;

@AllArgsConstructor
public class Converter {

    public static TicketEntity convertToTicketEntity(AirFlight airFlight) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setDateToIssue(airFlight.getDepartDate());
        ticketEntity.setPrice(airFlight.getPrice());
        ticketEntity.setTransfers(airFlight.getTransfers());
        ticketEntity.setEtkt(Generator.generateETKT());
        ticketEntity.setActual(true);
        ticketEntity.setDuration(airFlight.getDuration());
        ticketEntity.setDurationTo(airFlight.getDurationTo());
        ticketEntity.setDurationBack(airFlight.getDurationBack());
        ticketEntity.setReturnTransfers(airFlight.getReturnTransfers());
        ticketEntity.setReturnDate(airFlight.getReturnDate());
        ticketEntity.setDepartureDate(airFlight.getDepartDate());
        ticketEntity.setRaceNumber(airFlight.getRaceNumber());
        return ticketEntity;
    }
}
