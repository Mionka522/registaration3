package com.example.registration.util;


import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals((clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email","","Человек с таким email уже зарегестрирован");
        }

    }
}
