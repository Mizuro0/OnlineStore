package org.mizuro.aviatickets.controllers;

import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final SessionRegistry sessionRegistry;
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService, SessionRegistry sessionRegistry) {
        this.userService = userService;
        this.sessionRegistry = sessionRegistry;
        logger.info("SessionRegistry injected into AdminController.");
    }

    @GetMapping("/adminPage")
    public String getAdminPage(Model model) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userService.getCurrentUser());
        return "admin/adminPage";
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logoutUserById(@RequestParam("userId") int userId) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            for (SessionInformation session : sessionRegistry.getAllSessions(principal, false)) {
                if (session.getPrincipal().equals(userId)) {
                    session.expireNow();
                }
            }
        }
        return "User logged out successfully";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(@RequestParam(value = "nonLocked", required = false) Boolean nonLocked, Model model) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        if (nonLocked != null) {
            model.addAttribute("users", userService.findAllByNonLocked(nonLocked));
        } else {
            model.addAttribute("users", userService.findAll());
        }
        return "admin/allUsers";
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") int id, Model model) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userService.findById(id));
        return "admin/user";
    }

    @PatchMapping("/changeLock/{id}")
    public String changeLock(@PathVariable("id") int id, Model model) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        userService.changeLock(id);
        return "redirect:/admin/user/" + id;
    }

    @PatchMapping("/changeRole/{id}")
    public String changeRole(@PathVariable("id") int id, Model model) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        UserEntity user = userService.findById(id);
        user.setNonLocked(true);
        userService.update(user.getId(), user);
        model.addAttribute("user", user);
        userService.changeRole(id);
        return "redirect:/admin/user/" + id;
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        if(!userService.getCurrentUser().isNonLocked() || !userService.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userService.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        userService.deleteById(id);
        return "redirect:/admin/allUsers";
    }
}
