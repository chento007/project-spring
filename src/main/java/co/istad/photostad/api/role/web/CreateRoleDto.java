package co.istad.photostad.api.role.web;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleDto(
        @NotBlank(message = "Role name is required!")
        String name
) {
}
