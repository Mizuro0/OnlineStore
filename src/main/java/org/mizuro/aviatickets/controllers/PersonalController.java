package org.mizuro.aviatickets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.*;
import org.mizuro.aviatickets.models.PassportDto;
import org.mizuro.aviatickets.models.SearchResultDto;
import org.mizuro.aviatickets.services.*;
import org.mizuro.aviatickets.utils.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final Logger logger = LoggerFactory.getLogger(PersonalController.class);
    private final UserService userService;
    private final TicketEntityService ticketEntityService;
    private final PassportService passportService;
    private final UserValidator userValidator;
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    @GetMapping("/cabinet")
    public String getPersonalPage(Model model) {
        if (!userService.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        model.addAttribute("userEntity", userService.getCurrentUser());
        logger.info("Personal page loaded: " + userService.getCurrentUser().getUsername());
        return "personal/cabinet";
    }

    @GetMapping("/history")
    public String getHistoryPage(Model model) {
        if (!userService.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        ticketEntityService.updateTicketsActualStatusForCurrentUser();
        List<TicketEntity> usersTickets = ticketEntityService.getUsersTickets(userService.getCurrentUser());
        model.addAttribute("currentUsersTickets", usersTickets);
        return "personal/history";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("userEntity") @Valid UserEntity userEntity, Model model, BindingResult bindingResult) {
        userValidator.validate(userEntity, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            logger.info("Binding errors: " + bindingResult.getAllErrors());
            return "personal/cabinet";
        }
        userService.update(userService.getCurrentUser().getId(), userEntity);
        return "redirect:/personal/cabinet";
    }

    @GetMapping("/documents")
    public String getDocumentsPage(Model model) {
        if (!userService.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        model.addAttribute("userEntity", userService.getCurrentUser());
        model.addAttribute("passportDto", new PassportDto());
        return "personal/documents";
    }

    @GetMapping("/cities")
    public ResponseEntity<List<SearchResultDto>> searchCities(@RequestParam("searchTerm") String searchTerm) {
        List<CityEntity> cities = cityService.searchCities(searchTerm);
        List<SearchResultDto> results = new ArrayList<>();
        if (cities.size() > 10) {
            cities = cities.subList(0, 10);
        }
        for (CityEntity city : cities) {
            SearchResultDto result = new SearchResultDto();
            result.setCity(city.getTitleRu());
            if (city.getRegionId() != null) {
                result.setRegion(cityService.findRegion(city).getTitleRu());
                result.setCountry(cityService.findCountry(city).getTitleRu());
            } else {
                result.setCountry(cityService.findCountry(city).getTitleRu());
            }
            results.add(result);
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryEntity>> searchCountries(@RequestParam("searchTerm") String searchTerm) {
        List<CountryEntity> countries = countryService.findCountriesByTitle(searchTerm);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<SearchResultDto>> searchRegions(@RequestParam("searchTerm") String searchTerm) {
        List<RegionEntity> regions = regionService.searchRegions(searchTerm);
        List<SearchResultDto> results = new ArrayList<>();
        if (regions.size() > 10) {
            regions = regions.subList(0, 10);
            for (RegionEntity region : regions) {
                SearchResultDto result = new SearchResultDto();
                if (region != null) {
                    result.setRegion(region.getTitleRu());
                    result.setCountry(regionService.getCountry(region).getTitleRu());
                }
                results.add(result);
            }
        } else {
            for (RegionEntity region : regions) {
                SearchResultDto result = new SearchResultDto();
                if (region != null) {
                    result.setRegion(region.getTitleRu());
                    result.setCountry(regionService.getCountry(region).getTitleRu());
                }
                results.add(result);
            }
        }
        return ResponseEntity.ok(results);
    }

    @PostMapping("/createPassport")
    public String createPassport(@ModelAttribute("passportDto") @Valid PassportDto passportDto,
                                 Model model, BindingResult bindingResult) {
        if (!userService.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        if (bindingResult.hasErrors()) {
            return "personal/documents";
        }
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setName(passportDto.getName());
        passportEntity.setSurname(passportDto.getSurname());
        passportEntity.setDateOfBirth(passportDto.getBirthDate());
        passportEntity.setNumber(passportDto.getNumber());
        passportEntity.setSerial(passportDto.getSerial());
        passportEntity.setIssueDate(passportDto.getIssueDate());
        passportEntity.setExpirationDate(passportDto.getExpirationDate());
        String country = passportDto.getNationality().trim();
        String city = passportDto.getBirthPlaceCity().split(",")[0].trim();
        passportEntity.setNationality(countryService.findCountryByTitle(country));
        passportEntity.setBirthPlace(cityService.findCityByTitle(city));
        passportService.save(passportEntity, userService.getCurrentUser().getId());
        return "redirect:/personal/documents";
    }


    @DeleteMapping("/deletePassport")
    public String deletePassport() {
        logger.info("Received request to delete passport");
        passportService.delete(userService.getCurrentUser().getPassport().getId());
        return "redirect:/personal/documents";
    }

    @GetMapping("/tickets/{id}")
    public String getTicketsPage(@PathVariable("id") int id, Model model) {
        if (!userService.getCurrentUser().isNonLocked()) {
            return "redirect:/logout";
        }
        model.addAttribute("ticket", ticketEntityService.findById(id).orElse(null));
        return "personal/ticket";
    }


    @GetMapping("/countriesNationality")
    @ResponseBody
    public List<CountryEntity> getCountries(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
        logger.info("Received request to get countries");
        List<CountryEntity> countries;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            countries = countryService.findCountriesByTitle(searchTerm);
        } else {
            countries = List.of();
        }
        logger.info("Found {} countries", countries.size());
        return countries;
    }

    @DeleteMapping("/deleteTicket/{id}")
    public String deleteTicket(@PathVariable("id") int id) {
        logger.info("Received request to delete ticket: {}", id);
        ticketEntityService.deleteById(id);
        return "redirect:/personal/history";
    }
}
