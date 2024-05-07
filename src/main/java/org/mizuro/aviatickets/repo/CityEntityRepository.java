package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.CityEntity;
import org.mizuro.aviatickets.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityEntityRepository extends JpaRepository<CityEntity, Integer> {
    List<CityEntity> findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(String title, String title1, String title2);

    CityEntity findByTitleRu(String s);
}