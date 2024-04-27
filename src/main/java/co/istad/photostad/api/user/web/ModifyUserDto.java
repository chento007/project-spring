package co.istad.photostad.api.user.web;

import co.istad.photostad.config.LocalDateTimeDeserializer;
import co.istad.photostad.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ModifyUserDto(
        @NotBlank(message = "Username is required!")
        String username,
        @NotBlank(message = "Family name is required!")
        String familyName,
        @NotBlank(message = "Given name is required!")
        String givenName,
        @NotBlank(message = "Gender is required!")
        String gender,
        @NotBlank(message = "Date of Birth is required!")
        String dob,
        @NotNull(message = "Avatar is required!")
        Integer avatar,
        @NotBlank(message = "Phone number is required!")
        String phoneNumber,
        @NotBlank(message = "Address is required!")
        String address,
        @NotBlank(message = "Biography is required!")
        String biography
) {
}
