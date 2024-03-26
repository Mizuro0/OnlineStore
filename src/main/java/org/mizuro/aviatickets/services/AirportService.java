package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.repo.AirportRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AirportService {
    private final AirportRepo airportRepo;

    public Optional<AirportEntity> findByIataCode(String iataCode) {
        return airportRepo.findByIataCode(iataCode);
    }
    public Optional<AirportEntity> findByName(String name) {
        return airportRepo.findByName(name);
    }
    public Optional<AirportEntity> findByCity(String city) {
        return airportRepo.findByCity(city);
    }

    public Optional<AirportEntity> findByCountry(String country) {
        return airportRepo.findByCountry(country);
    }

    public List<AirportEntity> getAirports() {
        return airportRepo.findAll();
    }

    public Optional<AirportEntity> findById(Integer id) {
        return airportRepo.findById(id);
    }

    public List<AirportEntity> findAllByCity(String city) {
        return airportRepo.findAllByCity(city);
    }

    public List<AirportEntity> searchAirports(String searchTerm) {
        return airportRepo.searchAirports(searchTerm);
    }
}
