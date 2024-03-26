package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.services.AirFlightService;
import org.mizuro.aviatickets.services.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class RacesController {
    private final AirFlightService airFlightService;
    private final Logger logger = LoggerFactory.getLogger(RacesController.class);
    private final AirportService airportService;

    @GetMapping("/selectionMenu")
    public String getRaces(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        return "main/selectionMenu";
    }
}
