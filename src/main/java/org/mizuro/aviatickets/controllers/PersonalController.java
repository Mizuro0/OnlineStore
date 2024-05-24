package org.mizuro.aviatickets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.*;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.models.SearchResultDto;
import org.mizuro.aviatickets.services.*;
import org.mizuro.aviatickets.utils.validators.PassportValidator;
import org.mizuro.aviatickets.utils.validators.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final Logger logger = LoggerFactory.getLogger(PersonalController.class);
    private final UserService userServiceImpl;
    private final TicketService ticketServiceImpl;
    private final PassportService passportServiceImpl;
    private final UserValidator userValidator;
    private final CountryService countryServiceImpl;
    private final RegionService regionServiceImpl;
    private final CityService cityServiceImpl;
    private final PassportValidator passportValidator;

    @GetMapping("/cabinet")
    public String getPersonalPage(Model model) {
        UserEntity currentUser = userServiceImpl.getCurrentUser();
        if(currentUser == null || !currentUser.isNonLocked()) {
            return "redirect:/logout";
        }

        model.addAttribute("userEntity", currentUser);
        BindingResult bindingResult = new BeanPropertyBindingResult(currentUser, "userEntity");
        model.addAttribute("org.springframework.validation.BindingResult.userEntity", bindingResult);

        return "personal/cabinet";
    }


    @GetMapping("/history")
    public String getHistoryPage(Model model) {
        if (!userServiceImpl.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        ticketServiceImpl.updateTicketsActualStatusForCurrentUser();
        List<TicketEntity> usersTickets = ticketServiceImpl.getUsersTickets(userServiceImpl.getCurrentUser());
        model.addAttribute("currentUsersTickets", usersTickets);
        return "personal/history";
    }

    @PatchMapping("/{id}/update")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute("userEntity") @Valid UserEntity userEntity, Model model, BindingResult bindingResult) {
        logger.info("Received request to update user: {}", userEntity);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/cabinet";
        }
        userServiceImpl.update(id, userEntity);
        return "redirect:/personal/cabinet";
    }

    @GetMapping("/documents")
    public String getDocumentsPage(Model model) {
        if (!userServiceImpl.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        model.addAttribute("userEntity", userServiceImpl.getCurrentUser());
        model.addAttribute("passportDto", new PassportDto());
        return "personal/documents";
    }

    @GetMapping("/cities")
    public ResponseEntity<List<SearchResultDto>> searchCities(@RequestParam("searchTerm") String searchTerm) {
        List<SearchResultDto> searchResults = passportServiceImpl.findBirthPlace(searchTerm).getBody();
        return passportServiceImpl.findBirthPlace(searchTerm);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryEntity>> searchCountries(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(countryServiceImpl.findCountriesByTitle(searchTerm));
    }

    @GetMapping("/regions")
    public ResponseEntity<List<RegionEntity>> searchRegions(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(regionServiceImpl.searchRegions(searchTerm));
    }

    @PostMapping("/createPassport")
    public String createPassport(@ModelAttribute("passportDto") @Valid PassportDto passportDto,
                                 Model model, BindingResult bindingResult) {
        if (!userServiceImpl.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        passportValidator.validate(passportDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/documents";
        }
        passportDto.setOwner(userServiceImpl.getCurrentUser());
        passportServiceImpl.save(passportDto, userServiceImpl.getCurrentUser().getId());
        return "redirect:/personal/documents";
    }


    @DeleteMapping("/deletePassport")
    public String deletePassport() {
        logger.info("Received request to delete passport");
        passportServiceImpl.delete(userServiceImpl.getCurrentUser().getPassport().getId());
        return "redirect:/personal/documents";
    }

    @GetMapping("/tickets/{id}")
    public String getTicketsPage(@PathVariable("id") int id, Model model) {
        if (!userServiceImpl.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        model.addAttribute("ticket", ticketServiceImpl.findById(id).orElse(null));
        return "personal/ticket";
    }


    @GetMapping("/countriesNationality")
    @ResponseBody
    public List<CountryEntity> getCountries(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
        logger.info("Received request to get countries");
        List<CountryEntity> countries;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            countries = countryServiceImpl.findCountriesByTitle(searchTerm);
        } else {
            countries = List.of();
        }
        logger.info("Found {} countries", countries.size());
        return countries;
    }

    @DeleteMapping("/deleteTicket/{id}")
    public String deleteTicket(@PathVariable("id") int id) {
        logger.info("Received request to delete ticket: {}", id);
        ticketServiceImpl.deleteById(id);
        return "redirect:/personal/history";
    }
}
