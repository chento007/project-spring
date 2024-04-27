package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.user.validation.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(
        @Email
        String email,
        @NotBlank(message = "Password is required!")
        @Password
        String password,
        @NotBlank(message = "ConfirmPassword is required!")
        @Password
        String confirmedPassword,
        @NotBlank(message = "verifiedCode is required!")
        String verifiedCode
) {
}
