package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.models.AirFlight;
import org.mizuro.aviatickets.models.FlightData;
import org.mizuro.aviatickets.models.Seat;
import org.mizuro.aviatickets.services.AirportService;
import org.mizuro.aviatickets.services.TicketEntityService;
import org.mizuro.aviatickets.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mizuro.aviatickets.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/races")
public class RacesController {

    private final Logger logger = LoggerFactory.getLogger(RacesController.class);
    private final AirportService airportService;
    private final UserService userService;
    private List<AirFlight> airFlightList;
    private final TicketEntityService ticketService;

    @GetMapping("/selectionMenu")
    public String getRaces(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        return "races/selectionMenu";
    }

    @PostMapping("/addFoundedRaces")
    public String getRacesPage(@RequestBody FlightData flightData, Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "races/selectionMenu";
        }
        logger.info("Received request to get founded races: {}", flightData);
        airFlightList = flightData.getData();
        model.addAttribute("airFlights", airFlightList);
        return "redirect:/races/foundedRaces";
    }

    @GetMapping("/foundedRaces")
    public String getRacesPage(Model model) {
        model.addAttribute("races", airFlightList);
        return "races/foundedRaces";
    }

    @GetMapping("/race/{id}")
    public String getRacesPage(@PathVariable("id") String id, Model model) {
        Seat[][] seats = Seat.createSeats(6, 21);
        AirFlight airFlight = airFlightList.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
        logger.info("Received request to get race: {}", airFlight);
        model.addAttribute("race", airFlight);
        logger.info(airFlight.getOrigin() + " -> " + airFlight.getDestination());
        model.addAttribute("seats", seats);
        return "races/race";
    }

    @PostMapping("/{id}/createTicket")
    public String addTicket(@PathVariable("id") String id, @RequestParam("seat") String seat) {

        if(userService.getCurrentUser().getPassport() == null) {
            return "redirect:/personal/documents";
        }
        AirFlight airFlight = airFlightList.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
        assert airFlight != null;
        TicketEntity ticket = Converter.convertToTicketEntity(airFlight);
        ticket.setSeat(seat);
        logger.info("Received request to create ticket for race: {}", ticket);
        ticketService.save(ticket, airFlight, userService.getCurrentUser());
        return "redirect:/personal/cabinet";
    }

}
