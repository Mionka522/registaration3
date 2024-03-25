package com.example.registration.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно иметь не менее 2 и не более 30 символов")
    @Email
    private String email;

    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно иметь не менее 2 и не более 30 символов")
    private String userName;

    private String role;
}
