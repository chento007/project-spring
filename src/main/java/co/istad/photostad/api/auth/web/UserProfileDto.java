package co.istad.photostad.api.auth.web;

import co.istad.photostad.api.image.Image;
import co.istad.photostad.api.image.web.ImageDto;
import co.istad.photostad.api.role.Role;

import java.util.List;

public record UserProfileDto (
        String uuid,
        String username,
        String familyName,
        String givenName,
        String gender,
        String dob,
        Image avatar,
        String avatarUrl,
        String phoneNumber,
        String address,
        String biography,
        String createdAt,
        String loggedInAt,
        String email,
        Boolean isDeleted
){
}
