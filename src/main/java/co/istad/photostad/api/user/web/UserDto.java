package co.istad.photostad.api.user.web;

import co.istad.photostad.api.image.Image;
import co.istad.photostad.api.role.Role;

import java.sql.Timestamp;
import java.util.List;

public record UserDto(
        Integer id,
        String uuid,
        String username,
        String familyName,
        String givenName,
        String gender,
        String dob,
        String phoneNumber,
        String address,
        String biography,
        String email,
        Image avatar,
        List<Role> roles,
        String createdAt,

        String loggedInAt,

        String updatedAt,

        Boolean isDeleted
) {
}
