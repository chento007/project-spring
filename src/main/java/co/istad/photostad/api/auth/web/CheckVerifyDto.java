package co.istad.photostad.api.auth.web;

import jakarta.validation.constraints.NotBlank;

public record CheckVerifyDto(
        @NotBlank(message = "Email is required!")
        String email,
        @NotBlank(message = "VerifiedCode is required!")
        String verifiedCode
) {
}
