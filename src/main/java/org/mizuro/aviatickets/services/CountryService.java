package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.CountryEntity;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    List<CountryEntity> getAllCountries();
    CountryEntity saveCountry(CountryEntity country);
    Optional<CountryEntity> getCountryById(Integer id);
    void deleteCountryById(int id);
    List<CountryEntity> findCountriesByTitle(String title);
    boolean existsCountryById(int id);
    long countCountries();
    Optional<CountryEntity> findById(int id);
    CountryEntity findCountryById(int id);
    CountryEntity findCountryByTitle(String nationality);

}
