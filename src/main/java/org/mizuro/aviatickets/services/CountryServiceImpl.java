package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.repo.CountryEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryEntityRepository countryRepository;
    private final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);


    @Override
    public CountryEntity saveCountry(CountryEntity country) {
        return countryRepository.save(country);
    }

    @Override
    public List<CountryEntity> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<CountryEntity> getCountryById(Integer id) {
        return countryRepository.findById(id);
    }

    @Override
    public void deleteCountryById(int id) {
        countryRepository.deleteById(id);
    }

    @Override
    @Cacheable("countries")
    public List<CountryEntity> findCountriesByTitle(String title) {
        return countryRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    @Override
    public boolean existsCountryById(int id) {
        return countryRepository.existsById(id);
    }

    @Override
    public long countCountries() {
        return countryRepository.count();
    }

    @Override
    public Optional<CountryEntity> findById(int id) {
        return countryRepository.findById(id);
    }

    @Override
    @Cacheable("countries")
    public CountryEntity findCountryById(int id) {
        logger.info("Find country by title: " + id);
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public CountryEntity findCountryByTitle(String nationality) {
        return countryRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(nationality, nationality, nationality).stream().findFirst().orElse(null);
    }

    public String print(String i) {
        return i;
    }

    public static String print(int i) {
        return String.valueOf(i);
    }
}
