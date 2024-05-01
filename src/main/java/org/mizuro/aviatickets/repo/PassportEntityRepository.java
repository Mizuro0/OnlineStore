package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PassportEntityRepository extends JpaRepository<PassportEntity, Integer> {
    Optional<PassportEntity> findByOwner(UserEntity userEntity);
    Optional<PassportEntity> findByNumber(String number);
    Optional<PassportEntity> findBySerial(String serial);
    List<PassportEntity> findAllByName(String name);
    List<PassportEntity> findAllBySurname(String surname);
    List<PassportEntity> findAllByBirthDate(String birthDate);
}