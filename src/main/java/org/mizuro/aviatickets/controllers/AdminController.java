package org.mizuro.aviatickets.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/adminPage")
    public String getAdminPage(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "admin/adminPage";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
        logger.info("Get all users{}", userService.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin/allUsers";
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/user";
    }

    @GetMapping("/changeLock/{id}")
    public String changeLock(@PathVariable("id") int id, Model model) {
        userService.changeLock(id);
        return "redirect:/admin/user/" + id;
    }

    @GetMapping("/changeRole/{id}")
    public String changeRole(@PathVariable("id") int id, Model model) {
        userService.changeRole(id);
        return "redirect:/admin/user/" + id;
    }
}
