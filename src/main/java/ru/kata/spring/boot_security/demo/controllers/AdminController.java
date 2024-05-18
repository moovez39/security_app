package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/")
        public String adminPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.getUser(id));
        return "/admin/user_info";
    }
}
