package org.mizuro.aviatickets.utils.validators;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.services.CityService;
import org.mizuro.aviatickets.services.CountryService;
import org.mizuro.aviatickets.services.PassportService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
public class PassportValidator implements Validator {
    private final PassportService passportService;
    private final CityService cityService;
    private final CountryService countryService;
    @Override
    public boolean supports(Class<?> clazz) {
        return PassportDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PassportDto passportDto = (PassportDto) target;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date birthDate;
        Date issueDate;
        Date expirationDate;
        try {
            issueDate = formatter.parse(passportDto.getIssueDate());
            expirationDate = formatter.parse(passportDto.getExpirationDate());
            birthDate = formatter.parse(passportDto.getBirthDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //TODO : add validation for birth place
        if(passportService.findByNumber(passportDto.getNumber()).isPresent() && passportService.findBySerial(passportDto.getSerial()).isPresent())
            errors.rejectValue("number", "", "This passport is already token");
        if(expirationDate.before(now))
            errors.rejectValue("expirationDate", "", "This passport is expired");
        if(birthDate.after(now) || birthDate.after(issueDate))
            errors.rejectValue("birthDate", "", "Please enter correct birth date");
        if(expirationDate.before(issueDate))
            errors.rejectValue("expirationDate", "", "Expiration date cannot be before issue date");

    }
}
