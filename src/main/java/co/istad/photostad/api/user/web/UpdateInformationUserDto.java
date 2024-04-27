package co.istad.photostad.api.user.web;

public record UpdateInformationUserDto(
        String phoneNumber,
        String dob,
        String address
        ) {
}
