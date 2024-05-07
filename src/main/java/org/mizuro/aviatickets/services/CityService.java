package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.CityEntity;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.entity.RegionEntity;
import org.mizuro.aviatickets.repo.CityEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {

    private final CityEntityRepository cityRepository;
    private final RegionService regionService;
    private final Logger logger = LoggerFactory.getLogger(CityService.class);
    private final CountryService countryService;

    // Сохранить город
    public CityEntity saveCity(CityEntity city) {
        return cityRepository.save(city);
    }

    // Получить все города
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<CityEntity> getCityById(int id) {
        return cityRepository.findById(id);
    }

    public void deleteCityById(int id) {
        cityRepository.deleteById(id);
    }

    public List<CityEntity> findCitiesByTitle(String title) {
        return cityRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    public boolean existsCityById(int id) {
        return cityRepository.existsById(id);
    }

    public long countCities() {
        return cityRepository.count();
    }

    @Cacheable("cities")
    public List<CityEntity> searchCities(String searchTerm) {
        return cityRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
    }
    @Cacheable("regions")
    public RegionEntity findRegion(CityEntity city) {
        return regionService.findRegionById(city.getRegionId());
    }

    @Cacheable("countries")
    public CountryEntity findCountry(CityEntity city) {
        return countryService.findCountryById(city.getCountryId());
    }

    public CityEntity findCityByTitle(String s) {
        return cityRepository.findByTitleRu(s);
    }
}
