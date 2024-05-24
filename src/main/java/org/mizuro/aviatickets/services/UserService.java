package org.mizuro.aviatickets.services;

import org.mizuro.aviatickets.entity.Role;
import org.mizuro.aviatickets.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity getCurrentUser();
    void save(UserEntity userEntity);
    void update(int id, UserEntity userEntity);
    UserEntity findById(int id);
    void deleteById(int id);
    UserEntity findByPassportId(int passportId);
    UserEntity findByPassportNumber(String passportNumber);
    UserEntity findByPassportSerial(String passportSerial);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    void changeLock(int id);
    void changeRole(int id);
    List<UserEntity> findAll();
    List<UserEntity> findAllByRole(Role role);
    List<UserEntity> findAllByActive(boolean active);
    List<UserEntity> findAllByNonLocked(boolean nonLocked);
    List<UserEntity> findAllByNickname(String nickname);
    boolean passwordStrengthCheck(String password);
}
