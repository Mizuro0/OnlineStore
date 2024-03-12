package org.mizuro.onlinestore.services;

import lombok.AllArgsConstructor;
import org.mizuro.onlinestore.entity.UserEntity;
import org.mizuro.onlinestore.repo.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

}
