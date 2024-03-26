package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.AirFlightEntity;
import org.mizuro.aviatickets.repo.AirFlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Date;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AirFlightService {
    private final AirFlightRepository airFlightRepository;

    @Transactional
    public void save(AirFlightEntity airFlightEntity) {
        airFlightRepository.save(airFlightEntity);
    }

    public Optional<AirFlightEntity> findById(Integer id) {
        return airFlightRepository.findById(id);
    }

    public Iterable<AirFlightEntity> findAll() {
        return airFlightRepository.findAll();
    }

    @Transactional
    public void deleteAllByActualFalse() {
        airFlightRepository.deleteAllByActualFalse();
    }

    @Transactional
    public void deleteAllByDepartureDateAndReturnDateBefore(Date departureDate, Date returnDate) {
        airFlightRepository.deleteAllByDepartureDateAndReturnDateBefore(departureDate, returnDate);
    }

}
