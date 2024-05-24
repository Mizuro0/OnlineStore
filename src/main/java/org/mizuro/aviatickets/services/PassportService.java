package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.PassportEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.models.SearchResultDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    List<PassportEntity> findAll();

    Optional<PassportEntity> findById(int id);
    void save(PassportDto passportDto, int owner_id);
    Optional<PassportEntity> findByOwner(UserEntity userEntity);
    Optional<PassportEntity> findByNumber(String number);
    Optional<PassportEntity> findBySerial(String serial);
    List<PassportEntity> findAllByName(String name);
    List<PassportEntity> findAllBySurname(String surname);
    List<PassportEntity> findAllByBirthDate(String birthDate);

    ResponseEntity<List<SearchResultDto>> findBirthPlace(String searchTerm);


    void update(int id, PassportEntity passportEntity);
    void delete(int id);
}
