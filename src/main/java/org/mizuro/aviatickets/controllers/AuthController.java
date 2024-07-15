package org.mizuro.aviatickets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.kafka.KafkaProducer;
import org.mizuro.aviatickets.services.UserService;
import org.mizuro.aviatickets.services.UserServiceImpl;
import org.mizuro.aviatickets.utils.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userServiceImpl;
    private final UserValidator userValidator;
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
        userValidator.validate(userEntity, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "auth/registration";
        }
        userServiceImpl.save(userEntity);
        userServiceImpl.sendConfirmToken();
        return "redirect:/auth/confirm";
    }

    @GetMapping("/confirm")
    public String getConfirmPage() {
        return "auth/confirm";
    }

    @PostMapping("/sendConfirmToken")
    public String sendConfirmToken() {
        userServiceImpl.sendConfirmToken();
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/auth/login";
    }

}
