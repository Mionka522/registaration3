package com.example.registration.controllers;

import com.example.registration.dto.AuthenticationDTO;
import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.security.JwtUtil;
import com.example.registration.service.RegistrationService;
import com.example.registration.service.UserService;
import com.example.registration.util.UserValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    String role;
    public AuthController(UserValidator userValidator, RegistrationService registrationService, UserService userService, JwtUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
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

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        System.out.println("authenticationDTO - "+authenticationDTO.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDTO.getEmail(),
                            authenticationDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Ошибка"));
        }
        Optional<User> user = userService.findByEmail(authenticationDTO.getEmail());
        user.ifPresent(value -> role = value.getRole());
        String token = jwtUtil.generateToken(authenticationDTO.getEmail(),role);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);
        System.out.println("Токен при авторизации - " + token);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }
    @ResponseBody
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        User user = convertToUser(userDTO);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Входные данные неверны"));
        }
        System.out.println("userDTO "+userDTO);
        registrationService.register(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        System.out.println("Токен при регистрации - "+token);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);
    }
    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO,User.class);
    }

}