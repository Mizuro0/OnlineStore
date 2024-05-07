package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.repo.CountryEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryService {

    private final CountryEntityRepository countryRepository;
    private final Logger logger = LoggerFactory.getLogger(CountryService.class);



    public CountryEntity saveCountry(CountryEntity country) {
        return countryRepository.save(country);
    }

    public List<CountryEntity> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<CountryEntity> getCountryById(Integer id) {
        return countryRepository.findById(id);
    }

    public void deleteCountryById(int id) {
        countryRepository.deleteById(id);
    }

    @Cacheable("countries")
    public List<CountryEntity> findCountriesByTitle(String title) {
        return countryRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    public boolean existsCountryById(int id) {
        return countryRepository.existsById(id);
    }

    public long countCountries() {
        return countryRepository.count();
    }

    public Optional<CountryEntity> findById(int id) {
        return countryRepository.findById(id);
    }

    @Cacheable("countries")
    public CountryEntity findCountryById(int id) {
        logger.info("Find country by title: " + id);
        return countryRepository.findById(id).orElse(null);
    }

    public CountryEntity findCountryByTitle(String nationality) {
        return countryRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(nationality, nationality, nationality).stream().findFirst().orElse(null);
    }
}
