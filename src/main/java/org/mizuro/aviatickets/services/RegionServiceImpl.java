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
public class RegionServiceImpl implements RegionService {

    private final RegionEntityRepository regionRepository;
    private final CountryService countryServiceImpl;



    @Override
    public RegionEntity saveRegion(RegionEntity region) {
        return regionRepository.save(region);
    }

    @Override
    public List<RegionEntity> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Optional<RegionEntity> getRegionById(int id) {
        return regionRepository.findById(id);
    }

    @Override
    public void deleteRegionById(int id) {
        regionRepository.deleteById(id);
    }

    @Override
    public List<RegionEntity> findRegionsByTitle(String title) {
        return regionRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, title);
    }

    @Override
    public boolean existsRegionById(int id) {
        return regionRepository.existsById(id);
    }

    @Override
    public long countRegions() {
        return regionRepository.count();
    }

    @Override
    @Cacheable("regions")
    public List<RegionEntity> searchRegions(String searchTerm) {
        return regionRepository.findByTitleRuContainingIgnoreCaseOrTitleUaContainingIgnoreCaseOrTitleEnContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
    }

    @Override
    public RegionEntity findRegionByTitle(String titleRu) {
        return regionRepository.findByTitleRu(titleRu);
    }

    @Cacheable("regions")
    public RegionEntity findRegionById(int id) {
        return regionRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable("countries")
    public CountryEntity getCountry(RegionEntity region) {
        return countryServiceImpl.findCountryById(region.getCountryId());
    }

    @Override
    public CountryEntity findCountry(RegionEntity region) {
        return countryServiceImpl.findCountryById(region.getCountryId());
    }
}
