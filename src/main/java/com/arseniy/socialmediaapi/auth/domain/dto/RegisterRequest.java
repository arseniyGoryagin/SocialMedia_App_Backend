package com.arseniy.socialmediaapi.auth.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {


    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 5, max = 255)
    private String password;


    @Email(message = "invalid email")
    private String email;

}
