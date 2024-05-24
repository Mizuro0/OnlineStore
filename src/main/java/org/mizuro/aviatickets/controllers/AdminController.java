package org.mizuro.aviatickets.controllers;

import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.mizuro.aviatickets.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userServiceImpl;
    private final SessionRegistry sessionRegistry;
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, SessionRegistry sessionRegistry) {
        this.userServiceImpl = userServiceImpl;
        this.sessionRegistry = sessionRegistry;
        logger.info("SessionRegistry injected into AdminController.");
    }

    @GetMapping("/adminPage")
    public String getAdminPage(Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userServiceImpl.getCurrentUser());
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

    @GetMapping("/user/{id}/documents")
    public String getUserDocuments(@PathVariable("id") int id, Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userServiceImpl.findById(id));
        return "admin/userDocuments";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(@RequestParam(value = "nonLocked", required = false) Boolean nonLocked, Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        if (nonLocked != null) {
            model.addAttribute("users", userServiceImpl.findAllByNonLocked(nonLocked));
        } else {
            model.addAttribute("users", userServiceImpl.findAll());
        }
        return "admin/allUsers";
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") int id, Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userServiceImpl.findById(id));
        return "admin/user";
    }

    @PatchMapping("/changeLock/{id}")
    public String changeLock(@PathVariable("id") int id, Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        userServiceImpl.changeLock(id);
        return "redirect:/admin/user/" + id;
    }

    @PatchMapping("/changeRole/{id}")
    public String changeRole(@PathVariable("id") int id, Model model) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        UserEntity user = userServiceImpl.findById(id);
        user.setNonLocked(true);
        userServiceImpl.update(user.getId(), user);
        model.addAttribute("user", user);
        userServiceImpl.changeRole(id);
        return "redirect:/admin/user/" + id;
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        if(!userServiceImpl.getCurrentUser().isNonLocked() || !userServiceImpl.getCurrentUser().getRole().name().equals("ROLE_ADMIN") || userServiceImpl.getCurrentUser() == null) {
            return "redirect:/logout";
        }
        userServiceImpl.deleteById(id);
        return "redirect:/admin/allUsers";
    }
}
