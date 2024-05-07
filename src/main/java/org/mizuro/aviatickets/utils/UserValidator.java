package org.mizuro.aviatickets.utils;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.mizuro.aviatickets.services.UserService;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity userEntity = (UserEntity) target;
        if(userService.findByEmail(userEntity.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already token");
        if(userService.findByUsername(userEntity.getUsername()).isPresent())
            errors.rejectValue("username", "", "This username is already token");

    }
}
