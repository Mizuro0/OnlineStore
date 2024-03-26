package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.dto.AirFlightDTO;
import org.mizuro.aviatickets.dto.FlightDataDTO;
import org.mizuro.aviatickets.entity.AirFlightEntity;
import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.services.AirFlightService;
import org.mizuro.aviatickets.services.AirportService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiRestController {

    private final ModelMapper modelMap;
    private final RestTemplate restTemplate;
    private final AirFlightService airFlightService;
    private final AirportService airportService;
    private final Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    @GetMapping("/races")
    public ResponseEntity<?> getRaces(@RequestParam(value = "originIata", required = false) String origin, @RequestParam(value = "destinationIata", required = false) String destination, @RequestParam(value = "tripClass", required = false) Integer tripClass,
                                      @RequestParam(value = "departDate", required = false) String departDate, @RequestParam(value = "returnDate", required = false) String returnDate) {

        logger.info("Received request for races with parameters: origin={}, destination={}, tripClass={}, departDate={}, returnDate={}", origin, destination, tripClass, departDate, returnDate);

        // Проверка наличия обязательных параметров
        if (origin == null || destination == null || departDate == null || returnDate == null) {
            logger.error("Missing required parameters");
            return ResponseEntity.badRequest().build();
        }

        String apiUrl = String.format("https://api.travelpayouts.com/v2/prices/week-matrix?currency=usd&trip_class=%d&origin=%s&destination=%s&show_to_affiliates=true&depart_date=%s&return_date=%s&token=6ab5e091706e696e119afb698e40a511", 0, origin, destination, departDate, returnDate);

        try {
            FlightDataDTO flightData = restTemplate.getForObject(apiUrl, FlightDataDTO.class);
            List<AirFlightEntity> airFlightEntities = convertToAirFlightEntity(flightData.getData());
            for (AirFlightEntity airFlightEntity : airFlightEntities) {
                airFlightService.save(airFlightEntity);
            }
            logger.info("Retrieved races data successfully");
            // Перенаправление на другой URL
            String redirectURL = "localhost:8080/?";
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectURL)).build();
        } catch (HttpClientErrorException e) {
            logger.error("Error while fetching races data: {}", e.getMessage());
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
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

    private List<AirFlightEntity> convertToAirFlightEntity(List<AirFlightDTO> airFlightDTOs) {
        List<AirFlightEntity> airFlightEntities = new ArrayList<>();
        for (AirFlightDTO airFlightDTO : airFlightDTOs) {
            AirFlightEntity airFlightEntity = modelMap.map(airFlightDTO, AirFlightEntity.class);
            airFlightEntity.setOrigin(airportService.findByIataCode(airFlightDTO.getOrigin()).orElse(null));
            airFlightEntity.setDestination(airportService.findByIataCode(airFlightDTO.getDestination()).orElse(null));
            airFlightEntities.add(airFlightEntity);
        }
        return airFlightEntities;
    }
}