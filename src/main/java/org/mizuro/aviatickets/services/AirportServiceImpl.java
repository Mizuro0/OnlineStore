package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.repo.AirportRepo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Cacheable(value = "airports", unless = "#result == null")
@Transactional(readOnly = true)
public class AirportServiceImpl implements AirportService {
    private final AirportRepo airportRepo;

    @Override
    public Optional<AirportEntity> findByIataCode(String iataCode) {
        return airportRepo.findByIataCode(iataCode);
    }

    @Override
    public Optional<AirportEntity> findByName(String name) {
        return airportRepo.findByName(name);
    }

    @Override
    public Optional<AirportEntity> findByCity(String city) {
        return airportRepo.findByCity(city);
    }

    @Override
    public Optional<AirportEntity> findByCountry(String country) {
        return airportRepo.findByCountry(country);
    }

    @Override
    public List<AirportEntity> getAirports() {
        return airportRepo.findAll();
    }

    @Override
    public Optional<AirportEntity> findById(Integer id) {
        return airportRepo.findById(id);
    }

    @Override
    public List<AirportEntity> findAllByCity(String city) {
        return airportRepo.findAllByCity(city);
    }

    @Override
    public List<AirportEntity> searchAirports(String searchTerm) {
        return airportRepo.searchAirports(searchTerm);
    }
}
