package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.user.validation.email.EmailUnique;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserWithGoogleDto(
        @NotBlank(message = "Email is required!")
        @Email
        @EmailUnique
        String email
) {
}
