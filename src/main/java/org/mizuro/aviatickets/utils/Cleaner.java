package org.mizuro.aviatickets.utils;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.services.AirFlightService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class Cleaner {
    private final AirFlightService airFlightService;
    @Scheduled(fixedDelay = 1000 * 36000 * 24)
    public void clean() {
        Date currentDate = new Date();
        //delete all flights that were created more than 30 days ago
        airFlightService.deleteAllByDepartureDateAndReturnDateBefore(currentDate, currentDate);
    }

}
