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
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
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
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }
    @Override
    @Transactional
    public void save(UserEntity userEntity) {
        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void update(int id, UserEntity userEntity) {
        userEntity.setId(id);
        userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(int id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity findByPassportId(int passportId) {
        return userEntityRepository.findByPassportId(passportId).orElse(null);
    }

    @Override
    public UserEntity findByPassportNumber(String passportNumber) {
        return userEntityRepository.findByPassportNumber(passportNumber).orElse(null);
    }

    @Override
    public UserEntity findByPassportSerial(String passportSerial) {
        return userEntityRepository.findByPassportSerial(passportSerial).orElse(null);
    }

    @Override
    public List<UserEntity> findAllByNickname(String nickname) {
        return userEntityRepository.findAllByNickname(nickname);
    }

    @Override
    public boolean passwordStrengthCheck(String password) {
        Pattern pattern = Pattern.compile("/(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}/g");
        return pattern.matcher(password).matches();
    }

    @Override
    public List<UserEntity> findAllByActive(boolean active) {
        return userEntityRepository.findAllByActive(active);
    }

    @Override
    public List<UserEntity> findAllByNonLocked(boolean nonLocked) {
        return userEntityRepository.findAllByNonLocked(nonLocked);
    }

    @Override
    public List<UserEntity> findAllByRole(Role role) {
        return userEntityRepository.findAllByRole(role);
    }

    @Override
    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }
    @Override
    @Transactional
    public void changeLock(int id) {
        UserEntity user = findById(id);
        user.setNonLocked(!findById(id).isNonLocked());
        userEntityRepository.save(user);
    }

    @Override
    @Transactional
    public void changeRole(int id) {
        UserEntity user = findById(id);
        user.setRole(Role.valueOf(user.getRole().name().equals("ROLE_USER") ? "ROLE_ADMIN" : "ROLE_USER"));
        userEntityRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userEntityRepository.deleteById(id);
    }
}
