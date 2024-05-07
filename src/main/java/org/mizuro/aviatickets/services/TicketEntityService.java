package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.repo.TicketEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketEntityService {
    private final TicketEntityRepository ticketEntityRepository;
    private final UserService userService;
    private final AirportService airportService;

    public Optional<TicketEntity> findByEtkt(String etkt) {
        return ticketEntityRepository.findByEtkt(etkt);
    }

    @Transactional
    public void save(TicketEntity ticket, AirFlight airFlight, UserEntity currentUser) {
        ticket.setFrom(airportService.findByIataCode(airFlight.getOrigin()).orElse(null));
        ticket.setTo(airportService.findByIataCode(airFlight.getDestination()).orElse(null));
        ticket.setOwner(currentUser);
        ticketEntityRepository.save(ticket);
    }

    private List<TicketEntity> findAll() {
        return ticketEntityRepository.findAll();
    }

    public Optional<TicketEntity> findById(int id) {
        return ticketEntityRepository.findById(id);
    }

    public List<TicketEntity> getUsersTickets(UserEntity currentUser) {
        return ticketEntityRepository.findByOwner(currentUser);
    }

    public List<TicketEntity> getActualUsersTickets(UserEntity currentUser, boolean actual) {
        return ticketEntityRepository.findByOwnerAndActual(currentUser, actual);
    }

    @Transactional
    public void update(int id, TicketEntity ticketEntity) {
        ticketEntity.setId(id);
        ticketEntityRepository.save(ticketEntity);
    }

    @Transactional
    public void updateTicketsActualStatusForCurrentUser() {
        UserEntity currentUser = userService.getCurrentUser();
        List<TicketEntity> tickets = ticketEntityRepository.findByOwner(currentUser);
        boolean isUpdated = false;

        for (TicketEntity ticket : tickets) {
            OffsetDateTime dateTime = OffsetDateTime.parse(ticket.getDateToIssue(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            if (dateTime.isBefore(OffsetDateTime.now()) && ticket.isActual()) {
                ticket.setActual(false);
                isUpdated = true;
            }
        }

        if (isUpdated) {
            ticketEntityRepository.saveAll(tickets);
        }
    }

    @Transactional
    public void deleteById(int id) {
        ticketEntityRepository.deleteById(id);
    }
}