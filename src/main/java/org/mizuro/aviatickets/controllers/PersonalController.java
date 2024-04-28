package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mizuro.aviatickets.services.UserService;
import org.mizuro.aviatickets.services.TicketEntityService;

@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final Logger logger = LoggerFactory.getLogger(PersonalController.class);
    private final UserService userService;
    private final TicketEntityService ticketEntityService;


    @GetMapping("/cabinet")
    public String getPersonalPage(Model model) {
        model.addAttribute("userEntity", userService.getCurrentUser());
        logger.info("Personal page loaded: " + userService.getCurrentUser().getUsername());
        return "user/personal";
    }

    @GetMapping("/history")
    public String getHistoryPage(Model model) {
        model.addAttribute("currentUsersTickets", ticketEntityService.getUsersTickets(userService.getCurrentUser()));
        return "user/history";
    }
}
