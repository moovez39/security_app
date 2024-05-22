package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleRepository roleRepo;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository, RoleRepository roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }


    @GetMapping("/")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/test";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable Long id, Model model) {
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("user", userService.getUser(id));
        User user = (User) model.getAttribute("user");
        System.out.println(user);
        return "/admin/user_info";
    }


    @GetMapping("/create_user")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepo.findAll());
        return "admin/create_user";
    }

    @PostMapping("/user/{id}")
    public String editUser(@Valid User editedUser,
                           BindingResult bindingResult, @PathVariable Long id,@ModelAttribute("roles") List<Role> roles, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepo.findAll());
            return "/admin/user_info";
        }
        userService.saveUser(editedUser);
        return "redirect:/admin/";

    }

    @PostMapping("/remove_user/{id}")
    public String removeUser(@ModelAttribute("user") User user, @PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/create_user";
        }
        userService.saveUser(user);
        System.out.println(user);
        return "redirect:/admin/";
    }
}
