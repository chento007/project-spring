package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.user.validation.password.Password;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Email is required!")
        String email,
        @NotBlank(message = "Password is required!")
        String password
) {
}
