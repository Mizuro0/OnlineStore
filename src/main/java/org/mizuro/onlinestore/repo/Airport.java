package org.mizuro.onlinestore.repo;

import org.mizuro.onlinestore.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Airport extends JpaRepository<AirportEntity, Integer> {
    Optional<AirportEntity> findByIataCode(String iataCode);
    Optional<AirportEntity> findByName(String name);
    Optional<AirportEntity> findByCity(String city);
    Optional<AirportEntity> findByCountry(String country);

}
