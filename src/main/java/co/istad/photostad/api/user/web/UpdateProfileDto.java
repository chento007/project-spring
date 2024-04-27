package co.istad.photostad.api.user.web;

import co.istad.photostad.api.user.validation.image.ImageIdConstraint;
import co.istad.photostad.config.LocalDateTimeDeserializer;
import co.istad.photostad.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateProfileDto(
        String username,
        String familyName,
        String givenName,
        String gender,
        @ImageIdConstraint
        Integer avatar,
        String phoneNumber,
        String address,
        String biography
) {
}
