package co.istad.photostad.api.user.web;

import co.istad.photostad.api.user.validation.email.EmailUnique;
import co.istad.photostad.api.user.validation.image.ImageIdConstraint;
import co.istad.photostad.api.user.validation.password.Password;
import co.istad.photostad.api.user.validation.role.RoleIdConstraint;
import co.istad.photostad.config.LocalDateTimeDeserializer;
import co.istad.photostad.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateUserDto(
        @NotBlank(message = "Family name is required!")
        String familyName,
        @NotBlank(message = "Given name is required!")
        String givenName,
        @NotBlank(message = "Username is required!")
        String username,
        @NotBlank(message = "Email is required!")
        @Email
        @EmailUnique
        String email,
        @NotBlank(message = "Password is required!")
        @Password
        String password,
        @NotNull(message = "Avatar is required!")
        @ImageIdConstraint
        Integer avatar,
        @NotNull(message = "Roles are required!")
        @RoleIdConstraint
        List<Integer> roleIds
) {
}
