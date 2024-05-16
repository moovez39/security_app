package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserServiceImpl userService;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
        public String adminPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }
}
