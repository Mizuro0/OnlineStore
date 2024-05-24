package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.CityEntity;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.entity.RegionEntity;
import org.mizuro.aviatickets.repo.CityEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityEntityRepository cityRepository;
    private final RegionService regionServiceImpl;
    private final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    private final CountryService countryServiceImpl;

    @Override
    public CityEntity saveCity(CityEntity city) {
        return cityRepository.save(city);
    }

    @Override
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<CityEntity> getCityById(int id) {
        return cityRepository.findById(id);
    }

    @Override
    public void deleteCityById(int id) {
        cityRepository.deleteById(id);
    }

    @Override
    public List<CityEntity> findCitiesByTitle(String title) {
        return cityRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    @Override
    public boolean existsCityById(int id) {
        return cityRepository.existsById(id);
    }

    @Override
    public long countCities() {
        return cityRepository.count();
    }

    @Override
    @Cacheable("cities")
    public List<CityEntity> searchCities(String searchTerm) {
        return cityRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
    }

    @Override
    @Cacheable("regions")
    public RegionEntity findRegion(CityEntity city) {
        return regionServiceImpl.findRegionById(city.getRegionId());
    }

    @Override
    @Cacheable("countries")
    public CountryEntity findCountry(CityEntity city) {
        return countryServiceImpl.findCountryById(city.getCountryId());
    }

    @Override
    @Cacheable("cities")
    public Optional<CityEntity> findCityByTitle(String s) {
        return cityRepository.findByTitleRu(s);
    }
}
