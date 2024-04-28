package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.repo.TicketEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketEntityService {
    private final TicketEntityRepository ticketEntityRepository;

    @Transactional
    public void save(TicketEntity ticketEntity) {
        ticketEntityRepository.save(ticketEntity);
    }

    private List<TicketEntity> findAll() { return ticketEntityRepository.findAll(); }

    private TicketEntity findById(int id) { return ticketEntityRepository.findById(id).orElse(null); }

    public List<TicketEntity> getUsersTickets(UserEntity currentUser) {
        return ticketEntityRepository.findByOwner(currentUser);
    }
}
