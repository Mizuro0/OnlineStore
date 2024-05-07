package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryEntityRepository extends JpaRepository<CountryEntity, Integer> {
    List<CountryEntity> findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(String title, String title1, String title2);

    CountryEntity findByTitleRu(String titleRu);


    CountryEntity findByTitleEn(String nationality);
}