package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionEntityRepository extends JpaRepository<RegionEntity, Integer> {
    List<RegionEntity> findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(String title, String title1, String title2);

    RegionEntity findByTitleRu(String titleRu);
}