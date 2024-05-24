package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.AirportEntity;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    Optional<AirportEntity> findByIataCode(String iataCode);
    Optional<AirportEntity> findByName(String name);
    Optional<AirportEntity> findByCity(String city);
    Optional<AirportEntity> findByCountry(String country);
    List<AirportEntity> getAirports();
    Optional<AirportEntity> findById(Integer id);
    List<AirportEntity> findAllByCity(String city);
    List<AirportEntity> searchAirports(String searchTerm);
}
