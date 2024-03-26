package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.AirFlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AirFlightRepository extends JpaRepository<AirFlightEntity, Integer> {
    void deleteAllByActualFalse();

    void deleteAllByDepartureDateAndReturnDateBefore(Date departureDate, Date returnDate);

}
