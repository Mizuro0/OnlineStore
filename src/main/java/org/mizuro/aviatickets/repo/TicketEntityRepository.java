package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, Integer> {
    List<TicketEntity> findByOwner(UserEntity userEntity);
}