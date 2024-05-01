package org.mizuro.aviatickets.repo;

import org.mizuro.aviatickets.entity.Role;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPassportNumber(String passportNumber);
    Optional<UserEntity> findByPassportSerial(String passportSerial);
    Optional<UserEntity> findByPassportId(int passport_id);

    List<UserEntity> findAllByRole(Role role);
    List<UserEntity> findAllByNickname(String nickname);
    List<UserEntity> findAllByNonLocked(boolean nonLocked);
    List<UserEntity> findAllByActive(boolean active);
}