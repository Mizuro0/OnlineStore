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
public class TicketServiceImpl implements TicketService {
    private final TicketEntityRepository ticketEntityRepository;
    private final UserService userServiceImpl;
    private final AirportService airportServiceImpl;

    @Override
    public Optional<TicketEntity> findByEtkt(String etkt) {
        return ticketEntityRepository.findByEtkt(etkt);
    }

    @Override
    @Transactional
    public void save(TicketEntity ticket, AirFlight airFlight, UserEntity currentUser) {
        ticket.setFrom(airportServiceImpl.findByIataCode(airFlight.getOrigin()).orElse(null));
        ticket.setTo(airportServiceImpl.findByIataCode(airFlight.getDestination()).orElse(null));
        ticket.setOwner(currentUser);
        ticketEntityRepository.save(ticket);
    }

    @Override
    public List<TicketEntity> findAll() {
        return ticketEntityRepository.findAll();
    }

    @Override
    public Optional<TicketEntity> findById(int id) {
        return ticketEntityRepository.findById(id);
    }

    @Override
    public List<TicketEntity> getUsersTickets(UserEntity currentUser) {
        return ticketEntityRepository.findByOwner(currentUser);
    }

    @Override
    public List<TicketEntity> getActualUsersTickets(UserEntity currentUser, boolean actual) {
        return ticketEntityRepository.findByOwnerAndActual(currentUser, actual);
    }

    @Override
    @Transactional
    public void update(int id, TicketEntity ticketEntity) {
        ticketEntity.setId(id);
        ticketEntityRepository.save(ticketEntity);
    }

    @Override
    @Transactional
    public void updateTicketsActualStatusForCurrentUser() {
        UserEntity currentUser = userServiceImpl.getCurrentUser();
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

    @Override
    @Transactional
    public void deleteById(int id) {
        ticketEntityRepository.deleteById(id);
    }
}