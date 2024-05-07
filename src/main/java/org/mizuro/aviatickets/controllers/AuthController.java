package org.mizuro.aviatickets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userEntity") @Valid UserEntity userEntity, Model model,
    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "auth/registration";
        }
        userService.save(userEntity);
        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "redirect:/login";
    }

}
