package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.repo.TicketEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketEntityService {
    private final TicketEntityRepository ticketEntityRepository;
    private final UserService userService;

    @Transactional
    public void save(TicketEntity ticketEntity) {
        ticketEntityRepository.save(ticketEntity);
    }

    private List<TicketEntity> findAll() {
        return ticketEntityRepository.findAll();
    }

    private TicketEntity findById(int id) {
        return ticketEntityRepository.findById(id).orElse(null);
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
}