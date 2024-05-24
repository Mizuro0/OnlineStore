package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.models.AirFlight;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    void save(TicketEntity ticket, AirFlight airFlight, UserEntity currentUser);
    List<TicketEntity> findAll();
    List<TicketEntity> getUsersTickets(UserEntity currentUser);

    void deleteById(int id);
    void updateTicketsActualStatusForCurrentUser();
    void update(int id, TicketEntity ticketEntity);
    List<TicketEntity> getActualUsersTickets(UserEntity currentUser, boolean actual);
    Optional<TicketEntity> findById(int id);
    Optional<TicketEntity> findByEtkt(String etkt);
}
