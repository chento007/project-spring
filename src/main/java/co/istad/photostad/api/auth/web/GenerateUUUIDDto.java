package co.istad.photostad.api.auth.web;

import jakarta.validation.constraints.NotBlank;

public record GenerateUUUIDDto(
        @NotBlank(message = "UUID is required.!")
        String uuid
) {
}
