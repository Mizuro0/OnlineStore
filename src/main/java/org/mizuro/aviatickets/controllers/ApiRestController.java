package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.models.FlightData;
import org.mizuro.aviatickets.services.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiRestController {

    private final RestTemplate restTemplate;
    private final AirportService airportService;
    private final Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    @PostMapping("/racesList")
    public ResponseEntity<List<AirFlight>> sendRaces(@RequestParam(value = "originIata", required = false) String origin, @RequestParam(value = "destinationIata", required = false) String destination, @RequestParam(value = "tripClass", required = false) int tripClass,
                                                     @RequestParam(value = "departDate", required = false) String departDate, @RequestParam(value = "returnDate", required = false) String returnDate) {

        logger.info("Received request for races with parameters: origin={}, destination={}, tripClass={}, departDate={}, returnDate={}", origin, destination, tripClass, departDate, returnDate);

        if (origin == null || destination == null || departDate == null || returnDate == null) {
            logger.error("Missing required parameters");
            return ResponseEntity.badRequest().build();
        }

        String apiUrl = "https://api.travelpayouts.com/v2/prices/week-matrix?currency=usd&trip_class=" + tripClass + "&origin=" + origin + "&destination=" + destination + "&show_to_affiliates=true&depart_date=" + departDate + "&return_date=" + returnDate + "&token=6ab5e091706e696e119afb698e40a511";

        try {
            // Получаем данные от apiUrl
            ResponseEntity<FlightData> response = restTemplate.getForEntity(apiUrl, FlightData.class);
            logger.info("Retrieved races data successfully");

            // Возвращаем список рейсов в ответе
            List<AirFlight> airFlightList = response.getBody().getData();
            return ResponseEntity.ok(airFlightList);
        } catch (HttpClientErrorException e) {
            logger.error("Error while fetching races data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching races data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/airports")
    public List<AirportEntity> searchAirports(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
        logger.info("Received request to search airports with search term: {}", searchTerm);

        List<AirportEntity> airports;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            airports = airportService.searchAirports(searchTerm);
        } else {
            airports = List.of();
        }

        logger.info("Found {} airports", airports.size());
        return airports;
    }
}