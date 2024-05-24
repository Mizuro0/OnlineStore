package org.mizuro.aviatickets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PassportDto {
    private UserService userService;
    private String name;
    private String surname;
    private String birthDate;
    private String number;
    private String serial;
    private String issueDate;
    private String expirationDate;
    private String nationality;
    private String birthPlaceCity;
    private UserEntity owner;
}
