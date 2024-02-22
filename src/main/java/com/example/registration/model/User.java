package com.example.registration.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ROLE")
    private String role;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно иметь не менее 2 и не более 30 символов")
    @Email
    @Column(name = "EMAIL")
    private String email;

    @NotEmpty(message = "Поле не должно быть пустым")
   // @Size(min = 2, max = 30, message = "Пароль должен быть не менее 2 и не более 30 символов")
    @Column(name = "PASSWORD")
    private String password;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя пользователя должно иметь не менее 2 и не более 30 символов")
    @Column(name = "USERNAME")
    private String userName;

}
