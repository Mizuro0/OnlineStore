package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface PassportEntityRepository extends JpaRepository<PassportEntity, Integer> {
    Optional<PassportEntity> findByPerson(UserEntity userEntity);
    Optional<PassportEntity> findByNumber(String number);
    Optional<PassportEntity> findBySerial(String serial);
    List<PassportEntity> findAllByName(String name);
    List<PassportEntity> findAllBySurname(String surname);
    List<PassportEntity> findAllByDateOfBirth(String birthDate);

    @Modifying
    @Query(value = "DELETE FROM passports WHERE id = :passportId", nativeQuery = true)
    void deleteById(@Param("passportId") int passportId);
}