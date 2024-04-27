package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.auth.AuthService;
import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.web.ModifyUserDto;
import co.istad.photostad.api.user.web.UserDto;
import co.istad.photostad.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public BaseRest<?> refreshToken(@Valid @RequestBody TokenDto tokenDto) {

        AuthDto authDto = authService.refreshToken(tokenDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Token has been refreshed success.")
                .data(authDto)
                .build();

    }

    @PostMapping("/register")
    public BaseRest<?> register(@Valid @RequestBody RegisterDto registerDto) {

        String email = authService.register(registerDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Register has been success.")
                .data(email)
                .build();

    }

    @PostMapping("/verify")
    public BaseRest<?> verify(@RequestParam(name = "email") String email) {

        authService.verify(email);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Please check your verified email box.")
                .data(email)
                .build();
    }

    @PostMapping("/check-verify")
    public BaseRest<?> checkVerify(@Valid @RequestBody CheckVerifyDto checkVerifyDto) {

        authService.checkVerify(checkVerifyDto.email(), checkVerifyDto.verifiedCode());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("You have been verified success.")
                .data(checkVerifyDto.email())
                .build();

    }

    @PostMapping("/login")
    public BaseRest<?> login(@Valid @RequestBody LoginDto loginDto) {

        var result = authService.login(loginDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("You have been login Success.")
                .data(result)
                .build();

    }

    @GetMapping("/dashboard/me")
    public BaseRest<?> getAuthProfile(Authentication authentication) {

        UserDto loggedInProfileDto = authService.getProfile(authentication);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have retrieved profile success.")
                .timestamp(LocalDateTime.now())
                .data(loggedInProfileDto)
                .build();
    }

    @GetMapping("/me")
    public BaseRest<?> getProfile(Authentication authentication) throws AuthenticationException {

        UserProfileDto loggedInProfileDto = authService.getProfileClient(authentication);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have retrieved profile success.")
                .timestamp(LocalDateTime.now())
                .data(loggedInProfileDto)
                .build();
    }

    @PutMapping("/update-profile/{id}")
    public BaseRest<?> updateAdmin(@PathVariable("id") Integer id, @Valid @RequestBody ModifyUserDto modifyUserDto) {

        UserDto userDto = authService.updateUserById(id, modifyUserDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have update profile success.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @PostMapping("/verify-forgot-password")
    public BaseRest<?> verifyForgot(@RequestParam(name = "email") String email) {

        authService.verifyForgotPassword(email);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Please check your verified email box.")
                .data(email)
                .build();
    }

    @PostMapping("/reset-password")
    public BaseRest<?> resetPassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {

        String resetPasswordResult = authService.resetPassword(changePasswordDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Your password has been changed success.")
                .data(resetPasswordResult)
                .build();
    }

    @PostMapping("/check-verify-forgot-password")
    public BaseRest<?> checkVerifyForgotPassword(@Valid @RequestBody CheckVerifyDto checkVerifyDto) {

        authService.checkVerifyWithForgotPassword(checkVerifyDto.email(), checkVerifyDto.verifiedCode());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("You have been verified success.")
                .data(checkVerifyDto.email())
                .build();

    }

    @PostMapping("/register-with-google")
    public BaseRest<?> registerWithGoogle(@Valid @RequestBody UserWithGoogleDto userWithGoogleDto) {

        UserWithGoogleDto user = authService.registerWithGoogle(userWithGoogleDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Register has been success.")
                .data(user)
                .build();

    }

    @PostMapping("/generate-uuid")
    public BaseRest<?> generateUUID(@Valid @RequestBody GenerateUUUIDDto generateUUUIDDto) {

        String uuid = authService.generateUUID(generateUUUIDDto.uuid());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Generate UUID has been success.")
                .data(uuid)
                .build();

    }

    @GetMapping("/check-uuid/{uuid}")
    public BaseRest<?> checkUUID(@PathVariable(name = "uuid") String generateUUUID) {

        UserEditorDto isFound = authService.shareUUID(generateUUUID);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Check UUID has been found success.")
                .data(isFound)
                .build();

    }

    @GetMapping("/email/{email}")
    public BaseRest<?> checkEmail(@PathVariable("email") String email) {

        boolean isFound = authService.findEmail(email);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Email has been found success.")
                .data(isFound)
                .build();

    }

}