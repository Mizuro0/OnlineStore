package org.mizuro.onlinestore.repo;

import org.mizuro.onlinestore.entity.AirFlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirFlightRepository extends JpaRepository<AirFlightEntity, Integer> {
}
