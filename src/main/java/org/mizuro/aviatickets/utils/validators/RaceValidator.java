package org.mizuro.aviatickets.utils.validators;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.services.AirportService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Component
public final class RaceValidator implements Validator {
    private final AirportService airportService;
    @Override
    public boolean supports(Class<?> clazz) {
        return AirFlight.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AirFlight airFlight = (AirFlight) target;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date();
        Date departureDate;
        Date arrivalDate;
        try {
            departureDate = formatter.parse(airFlight.getDepartDate());
            arrivalDate = formatter.parse(airFlight.getReturnDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(airFlight.getOrigin() == null)
            errors.rejectValue("departureAirport", "", "Please enter correct departure airport");
        if(airFlight.getDestination() == null)
            errors.rejectValue("arrivalAirport", "", "Please enter correct arrival airport");
        if(airportService.findByName(airFlight.getOrigin()).isEmpty() || airportService.findAllByCity(airFlight.getOrigin()).isEmpty() || airportService.findByIataCode(airFlight.getOrigin()).isEmpty())
            errors.rejectValue("departureAirport", "", "Departure airport not found");
        if(airportService.findByName(airFlight.getDestination()).isEmpty() || airportService.findAllByCity(airFlight.getDestination()).isEmpty() || airportService.findByIataCode(airFlight.getDestination()).isEmpty())
            errors.rejectValue("arrivalAirport", "", "Arrival airport not found");
        if(departureDate.before(now))
            errors.rejectValue("departureDate", "", "Departure date cannot be in the past");
        if(arrivalDate.before(departureDate))
            errors.rejectValue("arrivalDate", "", "Arrival date cannot be before departure date");
    }
}
