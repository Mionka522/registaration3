package com.example.registration.controllers;


import com.example.registration.model.User;
import com.example.registration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()

    public String info(Authentication authentication, Model model) {

        model.addAttribute("user", authentication.getPrincipal());

        return "/user/info";
    }
    @GetMapping("/profile")
    //список людей
    public String yes(Authentication authentication, Model model) {

        model.addAttribute("user", authentication.getPrincipal());

        return "/user/profile";
    }


}

