package com.ibdev.instazoo.payload.request;

import com.ibdev.instazoo.annotations.PasswordMatches;
import com.ibdev.instazoo.annotations.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordMatches
public class SignUpRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please, enter your name")
    private String firstname;
    @NotEmpty(message = "Please, enter your last name")
    private String lastname;
    @NotEmpty(message = "Please, enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 10)
    private String password;
    private String confirmPassword;
}
