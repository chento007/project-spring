package co.istad.photostad.api.role.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDto(
        @NotNull(message = "Id is required!")
        Integer id,
        @NotBlank(message = "Name is required!")
        String name
) {
}
