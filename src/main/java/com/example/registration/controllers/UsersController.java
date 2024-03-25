package com.example.registration.controllers;


import com.example.registration.model.User;
import com.example.registration.security.MyUserDetails;
import com.example.registration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/yes")
    public String yes() {
        return "/user/yes";
    }

    @ResponseBody
    @GetMapping("/name")
    public String name() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }


}

