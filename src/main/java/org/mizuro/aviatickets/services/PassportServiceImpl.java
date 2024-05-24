package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.*;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.models.SearchResultDto;
import org.mizuro.aviatickets.repo.PassportEntityRepository;
import org.mizuro.aviatickets.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PassportServiceImpl implements PassportService {

    private final PassportEntityRepository passportRepo;
    private final UserService userServiceImpl;
    private final CityService cityServiceImpl;
    private final RestTemplate restTemplate;
    private final RegionService regionServiceImpl;
    private final CountryService countryServiceImpl;
    private final Converter converter;
    private final Logger logger = LoggerFactory.getLogger(PassportServiceImpl.class);

    @Override
    @Transactional
    public void save(PassportDto passportDto, int owner_id) {
        PassportEntity passportEntity = new PassportEntity();
        passportRepo.save(converter.convertToPassportEntity(passportDto));
    }

    @Override
    public Optional<PassportEntity> findByOwner(UserEntity userEntity) {
        return passportRepo.findByPerson(userServiceImpl.getCurrentUser());
    }

    @Override
    public Optional<PassportEntity> findById(int id) {
        return passportRepo.findById(id);
    }

    @Override
    @Transactional
    public void update(int id, PassportEntity passportEntity) {
        passportEntity.setId(id);
        passportRepo.save(passportEntity);
    }

    @Override
    public Optional<PassportEntity> findByNumber(String number) {
        return passportRepo.findByNumber(number);
    }

    @Override
    public Optional<PassportEntity> findBySerial(String serial) {
        return passportRepo.findBySerial(serial);
    }

    @Override
    public List<PassportEntity> findAllByName(String name) {
        return passportRepo.findAllByName(name);
    }

    @Override
    public List<PassportEntity> findAllBySurname(String surname) {
        return passportRepo.findAllBySurname(surname);
    }

    @Override
    public List<PassportEntity> findAllByBirthDate(String birthDate) {
        return passportRepo.findAllByDateOfBirth(birthDate);
    }

    @Override
    public ResponseEntity<List<SearchResultDto>> findBirthPlace(String searchTerm) {
        List<CityEntity> cities = cityServiceImpl.searchCities(searchTerm);
        List<SearchResultDto> results = new ArrayList<>();
        if(!cities.isEmpty()){
            if(cities.size() > 10) {
                cities = cities.subList(0, 10);
                cities.forEach(city -> {
                    searchResultsForCities(results, city);
                });
            } else {
                cities.forEach(city -> {
                    searchResultsForCities(results, city);
                });
            }
        }
        return ResponseEntity.ok(results);
    }

    private void searchResultsForCities(List<SearchResultDto> results, CityEntity city) {
        SearchResultDto result = new SearchResultDto();
        result.setCity(city.getTitleRu());
        if (city.getRegionId() != null) {
            result.setRegion(cityServiceImpl.findRegion(city).getTitleRu());
            result.setCountry(cityServiceImpl.findCountry(city).getTitleRu());
        } else {
            result.setRegion("");
            result.setCountry(cityServiceImpl.findCountry(city).getTitleRu().trim().replace(",", ""));
        }
        results.add(result);
    }

    @Override
    public List<PassportEntity> findAll() {
        return passportRepo.findAll();
    }

    @Override
    @Transactional
    public void delete(int id) {
        logger.info("Deleting passport with id: " + id);
        passportRepo.deleteById(id);
    }

}
