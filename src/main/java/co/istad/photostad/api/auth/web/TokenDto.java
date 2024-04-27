package co.istad.photostad.api.auth.web;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(
        @NotBlank(message = "RefreshToken is required!")
        String refreshToken

) {
}
