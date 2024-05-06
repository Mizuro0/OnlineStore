package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.Role;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.repo.UserEntityRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UsernameNotFoundException("Current user not found");
        }

        String username = authentication.getName();
        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
    }

    private BCryptPasswordEncoder encoder() {return new BCryptPasswordEncoder();}

    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Transactional
    public void save(UserEntity userEntity) {
        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
    }

    @Transactional
    public void update(int id, UserEntity userEntity) {
        userEntity.setId(id);
        userEntityRepository.save(userEntity);
    }

    public UserEntity findById(int id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    public UserEntity findByPassportId(int passportId) {
        return userEntityRepository.findByPassportId(passportId).orElse(null);
    }

    public UserEntity findByPassportNumber(String passportNumber) {
        return userEntityRepository.findByPassportNumber(passportNumber).orElse(null);
    }

    public UserEntity findByPassportSerial(String passportSerial) {
        return userEntityRepository.findByPassportSerial(passportSerial).orElse(null);
    }

    public List<UserEntity> findAllByNickname(String nickname) {
        return userEntityRepository.findAllByNickname(nickname);
    }

    public List<UserEntity> findAllByActive(boolean active) {
        return userEntityRepository.findAllByActive(active);
    }

    public List<UserEntity> findAllByNonLocked(boolean nonLocked) {
        return userEntityRepository.findAllByNonLocked(nonLocked);
    }

    public List<UserEntity> findAllByRole(Role role) {
        return userEntityRepository.findAllByRole(role);
    }

    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }
}
