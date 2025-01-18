package com.arseniy.socialmediaapi.auth.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {


    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 30, message = "Username must be longer than 5 characters")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 5, max = 30, message = "Password must be long then 5 characters")
    private String password;

    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "name must not be blank")
    @Size(min = 5, max = 30, message = "Username must be longer than 5 characters")
    private String name;

}
