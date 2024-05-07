package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketEntityRepository extends JpaRepository<TicketEntity, Integer> {
    List<TicketEntity> findByOwner(UserEntity userEntity);
    List<TicketEntity> findByOwnerAndActual(UserEntity userEntity, boolean actual);
    Optional<TicketEntity> findBySeat(String seat);
    Optional<TicketEntity> findByEtkt(String etkt);

    Optional<TicketEntity> findById(int id);

    Optional<TicketEntity> findByFromAndTo(AirportEntity from, AirportEntity to);

    List<TicketEntity> findAllByDateToIssue(String dateToIssue);
    List<TicketEntity> findAllByFrom(AirportEntity from);
    List<TicketEntity> findAllByTo(AirportEntity to);

    @Modifying
    @Query(value = "DELETE FROM tickets WHERE id = :ticketId", nativeQuery = true)
    void deleteById(@Param("ticketId") int ticketId);
}