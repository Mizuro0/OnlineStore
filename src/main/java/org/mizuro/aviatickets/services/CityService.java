package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.CityEntity;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.entity.RegionEntity;

import java.util.List;
import java.util.Optional;

public interface CityService {
    CityEntity saveCity(CityEntity city);

    Optional<CityEntity> getCityById(int id);

    Optional<CityEntity> findCityByTitle(String s);

    List<CityEntity> getAllCities();

    List<CityEntity> findCitiesByTitle(String title);
    boolean existsCityById(int id);
    long countCities();
    List<CityEntity> searchCities(String searchTerm);
    RegionEntity findRegion(CityEntity city);
    CountryEntity findCountry(CityEntity city);

    void deleteCityById(int id);
}
