package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.entity.RegionEntity;

import java.util.List;
import java.util.Optional;

public interface RegionService {

    List<RegionEntity> getAllRegions();

    Optional<RegionEntity> getRegionById(int id);
    RegionEntity saveRegion(RegionEntity region);
    void deleteRegionById(int id);
    List<RegionEntity> findRegionsByTitle(String title);
    boolean existsRegionById(int id);
    long countRegions();
    List<RegionEntity> searchRegions(String searchTerm);
    RegionEntity findRegionByTitle(String titleRu);
    RegionEntity findRegionById(int id);
    CountryEntity getCountry(RegionEntity region);
    CountryEntity findCountry(RegionEntity region);
}
