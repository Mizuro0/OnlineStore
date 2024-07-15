package org.mizuro.aviatickets.utils.validators;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.mizuro.aviatickets.services.UserServiceImpl;

@Component
@AllArgsConstructor
public final class UserValidator implements Validator {
    private final UserService userServiceImpl;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity userEntity = (UserEntity) target;
        if(userServiceImpl.findByEmail(userEntity.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already token");
        if(userServiceImpl.findByUsername(userEntity.getUsername()).isPresent())
            errors.rejectValue("username", "", "This username is already token");
        if(!userServiceImpl.passwordStrengthCheck(userEntity.getPassword())) {
            errors.rejectValue("password", "", "Password is too weak. Password must be at least 6 characters long, " + "contain at least one digit, one uppercase, one special character and one lowercase letter");
        }
    }
}
