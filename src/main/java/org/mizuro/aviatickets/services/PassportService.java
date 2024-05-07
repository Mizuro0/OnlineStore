package org.mizuro.aviatickets.services;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.PassportEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.repo.PassportEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PassportService {

    private final PassportEntityRepository passportRepo;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(PassportService.class);

    @Transactional
    public void save(PassportEntity passportEntity, int owner_id) {
        passportEntity.setPerson(userService.findById(owner_id));
        passportRepo.save(passportEntity);
    }

    public Optional<PassportEntity> findByOwner(UserEntity userEntity) {
        return passportRepo.findByPerson(userService.getCurrentUser());
    }

    public Optional<PassportEntity> findById(int id) {
        return passportRepo.findById(id);
    }

    @Transactional
    public void update(int id, PassportEntity passportEntity) {
        passportEntity.setId(id);
        passportRepo.save(passportEntity);
    }

    public Optional<PassportEntity> findByNumber(String number) {
        return passportRepo.findByNumber(number);
    }

    public Optional<PassportEntity> findBySerial(String serial) {
        return passportRepo.findBySerial(serial);
    }

    public List<PassportEntity> findAllByName(String name) {
        return passportRepo.findAllByName(name);
    }

    public List<PassportEntity> findAllBySurname(String surname) {
        return passportRepo.findAllBySurname(surname);
    }

    public List<PassportEntity> findAllByBirthDate(String birthDate) {
        return passportRepo.findAllByDateOfBirth(birthDate);
    }

    public List<PassportEntity> findAll() {
        return passportRepo.findAll();
    }

    @Transactional
    public void delete(int id) {
        logger.info("Deleting passport with id: " + id);
        passportRepo.deleteById(id);
    }

}
