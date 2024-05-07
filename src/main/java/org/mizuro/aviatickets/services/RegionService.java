package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.CountryEntity;
import org.mizuro.aviatickets.entity.RegionEntity;
import org.mizuro.aviatickets.repo.RegionEntityRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {

    private final RegionEntityRepository regionRepository;
    private final CountryService countryService;



    // Сохранить регион
    public RegionEntity saveRegion(RegionEntity region) {
        return regionRepository.save(region);
    }

    // Получить все регионы
    public List<RegionEntity> getAllRegions() {
        return regionRepository.findAll();
    }

    // Получить регион по его ID
    public Optional<RegionEntity> getRegionById(int id) {
        return regionRepository.findById(id);
    }

    // Удалить регион по его ID
    public void deleteRegionById(int id) {
        regionRepository.deleteById(id);
    }

    // Найти регионы по названию (любому из языков)
    public List<RegionEntity> findRegionsByTitle(String title) {
        return regionRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    // Проверить, существует ли регион с данным ID
    public boolean existsRegionById(int id) {
        return regionRepository.existsById(id);
    }

    // Получить количество регионов
    public long countRegions() {
        return regionRepository.count();
    }

    @Cacheable("regions")
    public List<RegionEntity> searchRegions(String searchTerm) {
        return regionRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
    }

    public RegionEntity findRegionByTitle(String titleRu) {
        return regionRepository.findByTitleRu(titleRu);
    }

    @Cacheable("regions")
    public RegionEntity findRegionById(int id) {
        return regionRepository.findById(id).orElse(null);
    }

    @Cacheable("countries")
    public CountryEntity getCountry(RegionEntity region) {
        return countryService.findCountryById(region.getCountryId());
    }

    public CountryEntity findCountry(RegionEntity region) {
        return countryService.findCountryById(region.getCountryId());
    }
}
