package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.user.validation.email.EmailUnique;
import co.istad.photostad.api.user.validation.password.Password;
import co.istad.photostad.api.user.validation.password.PasswordMatch;
import co.istad.photostad.api.user.validation.role.RoleIdConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@PasswordMatch(password = "password", confirmedPassword = "confirmedPassword")

public record RegisterDto(
        @NotBlank(message = "Email is required!")
        @Email
        @EmailUnique
        String email,
        @NotBlank(message = "Password is required!")
        @Password
        String password,
        @NotBlank(message = "ConfirmPassword is required!")
        @Password
        String confirmedPassword
) {
}
