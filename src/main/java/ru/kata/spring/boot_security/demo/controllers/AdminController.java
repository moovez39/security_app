package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public AdminController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        User user = (User) model.getAttribute("user");
        System.out.println(user);
        return "/admin/user_info";
    }


    @PostMapping("user/edit_user/{id}")
    public String editUser(@ModelAttribute("user") User editedUser, @PathVariable Long id) {
        System.out.println(editedUser);
        userService.editUser(editedUser, id);
        return "redirect:/admin/";

    }

    @PostMapping("/remove_user/{id}")
    public String removeUser(@ModelAttribute("user") User user, @PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/create_user")
    public String createUserPage(Model model){
        model.addAttribute("user",new User());
        return "admin/user_info";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("user") User user){
        userRepository.save(user);
        return "redirect:/admin/";
    }
}
