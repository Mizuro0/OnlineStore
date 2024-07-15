package org.mizuro.aviatickets.utils;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.PassportEntity;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.services.CityService;
import org.mizuro.aviatickets.services.CountryService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Converter {
    private final CountryService countryServiceImpl;
    private final CityService cityServiceImpl;

    public final TicketEntity convertToTicketEntity(AirFlight airFlight) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setDateToIssue(airFlight.getDepartDate());
        ticketEntity.setPrice(airFlight.getPrice());
        ticketEntity.setTransfers(airFlight.getTransfers());
        ticketEntity.setEtkt(Generator.generateETKT());
        ticketEntity.setActual(true);
        ticketEntity.setDuration(airFlight.getDuration());
        ticketEntity.setDurationTo(airFlight.getDurationTo());
        ticketEntity.setDurationBack(airFlight.getDurationBack());
        ticketEntity.setReturnTransfers(airFlight.getReturnTransfers());
        ticketEntity.setReturnDate(airFlight.getReturnDate());
        ticketEntity.setDepartureDate(airFlight.getDepartDate());
        ticketEntity.setRaceNumber(airFlight.getRaceNumber());
        return ticketEntity;
    }
    public final PassportEntity convertToPassportEntity(PassportDto passportDto) {
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setName(passportDto.getName());
        passportEntity.setSurname(passportDto.getSurname());
        passportEntity.setDateOfBirth(passportDto.getBirthDate());
        passportEntity.setNumber(passportDto.getNumber());
        passportEntity.setSerial(passportDto.getSerial());
        passportEntity.setIssueDate(passportDto.getIssueDate());
        passportEntity.setExpirationDate(passportDto.getExpirationDate());
        String country = passportDto.getNationality().trim();
        String city = passportDto.getBirthPlaceCity().split(",")[0].trim();
        passportEntity.setNationality(countryServiceImpl.findCountryByTitle(country));
        passportEntity.setBirthPlace(cityServiceImpl.findCityByTitle(city).orElseThrow(() -> new IllegalArgumentException("City not found")));
        passportEntity.setPerson(passportDto.getOwner());
        return passportEntity;
    }
}
