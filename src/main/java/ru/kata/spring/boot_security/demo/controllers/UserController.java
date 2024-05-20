package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@Secured("ADMIN")
public class UserController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";

    }

    @GetMapping("/registration")
    public String registerUser(Model model) {
        model.addAttribute("new_user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("new_user") User new_user) {
        userService.saveUser(new_user);
        System.out.println("test");
        return "redirect:/";
    }
}

