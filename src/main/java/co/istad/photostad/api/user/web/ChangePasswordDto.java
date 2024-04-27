package co.istad.photostad.api.user.web;

import co.istad.photostad.api.user.validation.password.Password;
import co.istad.photostad.api.user.validation.password.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@PasswordMatch(password = "newPassword", confirmedPassword = "confirmedPassword")
public record ChangePasswordDto(

        @NotBlank(message = "OldPassword is required!")
        String oldPassword,

        @NotBlank(message = "NewPassword is required!")
        @Password
        String newPassword,

        @NotBlank(message = "ConfirmedPassword is required!")
        @Password
        String confirmedPassword

) {
}
