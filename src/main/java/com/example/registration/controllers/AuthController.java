package com.example.registration.controllers;

import com.example.registration.model.User;
import com.example.registration.service.RegistrationService;
import com.example.registration.service.UserService;
import com.example.registration.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final UserService userService;
    public AuthController(UserValidator userValidator, RegistrationService registrationService, UserService userService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userService = userService;
    }
    @GetMapping()
    public String welcome(Model model) {
        model.addAttribute("user", userService.findAll());
        return "/auth/welcome";
    }
    @GetMapping("/login")
    public String login() {
        System.out.println(userService.findAll());
        return "/auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user,bindingResult);
        if(bindingResult.hasErrors())
            return "auth/registration";
        registrationService.register(user);
        return "redirect:/auth/login";
    }

}