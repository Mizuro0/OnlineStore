package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.PassportEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.repo.PassportEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PassportService {

    private final PassportEntityRepository passportRepo;
    private final UserService userService;

    @Transactional
    public void save(PassportEntity passportEntity, int owner_id) {
        passportEntity.setOwner(userService.findById(owner_id));
        passportRepo.save(passportEntity);
        userService.getCurrentUser().setPassport(findByOwner(userService.getCurrentUser()));
        userService.update(owner_id, userService.findById(owner_id));
    }

    public PassportEntity findByOwner(UserEntity userEntity) {
        return passportRepo.findByOwner(userService.getCurrentUser()).orElse(null);
    }

    public PassportEntity findById(int id) {
        return passportRepo.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, PassportEntity passportEntity) {
        passportEntity.setId(id);
        passportRepo.save(passportEntity);
    }

    public PassportEntity findByNumber(String number) {
        return passportRepo.findByNumber(number).orElse(null);
    }

    public PassportEntity findBySerial(String serial) {
        return passportRepo.findBySerial(serial).orElse(null);
    }

    public List<PassportEntity> findAllByName(String name) {
        return passportRepo.findAllByName(name);
    }

    public List<PassportEntity> findAllBySurname(String surname) {
        return passportRepo.findAllBySurname(surname);
    }

    public List<PassportEntity> findAllByBirthDate(String birthDate) {
        return passportRepo.findAllByBirthDate(birthDate);
    }

    public List<PassportEntity> findAll() {
        return passportRepo.findAll();
    }
}
