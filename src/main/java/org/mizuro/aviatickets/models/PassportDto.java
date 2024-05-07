package org.mizuro.aviatickets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    private String name;
    private String surname;
    private String birthDate;
    private String number;
    private String serial;
    private String issueDate;
    private String expirationDate;
    private String nationality;
    private String birthPlaceCity;
}
