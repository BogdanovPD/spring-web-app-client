package com.bogdanovpd.spring.webapp.spring.webapp.client.controller;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.Role;
import com.bogdanovpd.spring.webapp.spring.webapp.client.model.User;
import com.bogdanovpd.spring.webapp.spring.webapp.client.service.RoleRestService;
import com.bogdanovpd.spring.webapp.spring.webapp.client.service.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRestService userRestService;
    @Autowired
    private RoleRestService roleRestService;

    @GetMapping("/")
    public String main(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("authUser", context.getAuthentication().getName());
        return "auth";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("err", "Invalid Credentials provided");
        }
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("authUser", context.getAuthentication().getName());
        return "auth";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userRestService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRestService.getAllRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("message", "You are logged in as "
                + context.getAuthentication().getName());
        return "user";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userRestService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRestService.getUserById(id));
        model.addAttribute("roles", roleRestService.getAllRoles());
        return "userForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        if (user.getId() == 0) {
            userRestService.addUser(user);
        } else {
            userRestService.updateUser(user);
        }
        return "redirect:/admin";
    }

}
