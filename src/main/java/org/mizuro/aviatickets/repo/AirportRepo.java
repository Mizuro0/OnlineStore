package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepo extends JpaRepository<AirportEntity, Integer> {
    Optional<AirportEntity> findByIataCode(String iataCode);
    Optional<AirportEntity> findByName(String name);
    Optional<AirportEntity> findByCity(String city);
    Optional<AirportEntity> findByCountry(String country);

    List<AirportEntity> findAllByCity(String city);

    @Query("SELECT a FROM AirportEntity a WHERE a.city LIKE %:searchTerm% OR a.name LIKE %:searchTerm%")
    List<AirportEntity> searchAirports(@Param("searchTerm") String searchTerm);
}
