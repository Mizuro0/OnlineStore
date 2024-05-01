package org.mizuro.aviatickets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.PassportEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.PassportService;
import org.mizuro.aviatickets.utils.TicketActuator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final PassportService passportService;


    @GetMapping("/cabinet")
    public String getPersonalPage(Model model) {
        model.addAttribute("userEntity", userService.getCurrentUser());
        logger.info("Personal page loaded: " + userService.getCurrentUser().getUsername());
        return "personal/cabinet";
    }

    @GetMapping("/history")
    public String getHistoryPage(Model model) {
        TicketActuator.checkActual();
        model.addAttribute("currentUsersTickets", ticketEntityService.getUsersTickets(userService.getCurrentUser()));
        return "personal/history";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("userEntity") @Valid UserEntity userEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/cabinet";
        }
        userService.update(userService.getCurrentUser().getId(), userEntity);
        return "redirect:/personal/cabinet";
    }

    @GetMapping("/documents")
    public String getDocumentsPage(Model model) {
        model.addAttribute("userEntity", userService.getCurrentUser());
        return "personal/documents";
    }

    @PostMapping("/personal/createPassport")
    public String createPassport(@ModelAttribute("passportEntity") @Valid PassportEntity passport, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/documents";
        }
        passportService.save(passport, userService.getCurrentUser().getId());
        return "redirect:/personal/documents";
    }

    @GetMapping("/personal/updatePage")
    public String updatePage(Model model) {
        model.addAttribute("passport", userService.getCurrentUser().getPassport());
        return "personal/updatePassport";
    }

    @PatchMapping("/personal/updatePassport")
    public String updatePassport(@ModelAttribute("passportEntity") @Valid PassportEntity passport, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/updatePassport";
        }

        userService.getCurrentUser().setPassport(passport);
        userService.update(userService.getCurrentUser().getId(), userService.getCurrentUser());
        return "redirect:/personal/cabinet";
    }
}
