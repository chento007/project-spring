package co.istad.photostad.api.user.web;

import co.istad.photostad.api.user.validation.image.ImageIdConstraint;

public record UpdateProfileClientDto(
        String username,
        String familyName,
        String givenName,
        @ImageIdConstraint
        Integer avatar,
        String gender,
        String biography
        ) {
}
