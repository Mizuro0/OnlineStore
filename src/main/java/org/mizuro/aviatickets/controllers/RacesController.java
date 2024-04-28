package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.models.FlightData;
import org.mizuro.aviatickets.services.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mizuro.aviatickets.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/races")
public class RacesController {
    private final Logger logger = LoggerFactory.getLogger(RacesController.class);
    private final AirportService airportService;
    private final UserService userService;
    private List<AirFlight> airFlightList;

    @GetMapping("/selectionMenu")
    public String getRaces(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        return "main/selectionMenu";
    }

    @PostMapping("/addFoundedRaces")
    public String getRacesPage(@RequestBody FlightData flightData, Model model) {
        logger.info("Received request to get founded races: {}", flightData);
        airFlightList = flightData.getData();
        model.addAttribute("airFlights", airFlightList);
        return "redirect:/races/foundedRaces";
    }

    @GetMapping("/foundedRaces")
    public String getRacesPage(Model model) {
        model.addAttribute("races", airFlightList);
        return "main/races";
    }
}
