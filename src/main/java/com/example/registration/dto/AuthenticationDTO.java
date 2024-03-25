package com.example.registration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationDTO {
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно иметь не менее 2 и не более 30 символов")
    @Email
    private String email;

    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;

}
