package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.services.AirportService;
import org.mizuro.aviatickets.services.PassportService;
import org.mizuro.aviatickets.services.TicketEntityService;
import org.mizuro.aviatickets.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminContoller {
    private final TicketEntityService ticketEntityService;
    private final UserService userService;
    private final PassportService passportService;
    private final AirportService airportService;
    private final Logger logger = LoggerFactory.getLogger(AdminContoller.class);

    @GetMapping("/adminPage")
    public String getAdminPage(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "admin/adminPage";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/allUsers";
    }
}
