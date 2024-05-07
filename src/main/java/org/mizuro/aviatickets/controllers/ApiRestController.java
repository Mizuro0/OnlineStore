package org.mizuro.aviatickets.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.AirportEntity;
import org.mizuro.aviatickets.models.FlightData;
import org.mizuro.aviatickets.services.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
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

    @PostMapping(value = "/racesList", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<FlightData> sendRaces(HttpServletRequest request) {
        String origin = request.getParameter("originIata");
        String destination = request.getParameter("destinationIata");
        int tripClass = Integer.parseInt(request.getParameter("tripClass"));
        String departDate = request.getParameter("departDate");
        String returnDate = request.getParameter("returnDate");

        logger.info("Received request for races with parameters: origin={}, destination={}, tripClass={}, departDate={}, returnDate={}", origin, destination, tripClass, departDate, returnDate);

        if (origin == null || destination == null || departDate == null || returnDate == null) {
            logger.error("Required parameters are missing: origin={}, destination={}, departDate={}, returnDate={}", origin, destination, departDate, returnDate);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String apiUrl = "https://api.travelpayouts.com/aviasales/v3/prices_for_dates?origin=" + origin + "&destination=" + destination + "&departure_at=" + departDate + "&return_at=" + returnDate + "&unique=false&sorting=price&direct=false&currency=rub&limit=30&page=1&one_way=true&token=6ab5e091706e696e119afb698e40a511";
        String controllerUrl = "http://localhost:8080/races/addFoundedRaces";

        try {
            ResponseEntity<FlightData> response = restTemplate.getForEntity(apiUrl, FlightData.class);
            HttpEntity<FlightData> httpEntity = new HttpEntity<>(response.getBody(), new HttpHeaders());
            restTemplate.exchange(controllerUrl, HttpMethod.POST, httpEntity, FlightData.class);
            logger.info("Retrieved races data successfully");
            return ResponseEntity.ok(response.getBody());
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
