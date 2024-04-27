package co.istad.photostad.api.auth.web;

public record AuthDto(
        String tokenType,
        String accessToken,
        String refreshToken
) {
}

