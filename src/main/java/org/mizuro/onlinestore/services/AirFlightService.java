package org.mizuro.onlinestore.services;

import lombok.AllArgsConstructor;
import org.mizuro.onlinestore.entity.AirFlightEntity;
import org.mizuro.onlinestore.repo.AirFlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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



}
