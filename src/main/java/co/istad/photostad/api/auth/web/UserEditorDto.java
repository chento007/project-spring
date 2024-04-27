package co.istad.photostad.api.auth.web;

public record UserEditorDto(
        Integer id,
        String uuid,
        String email,
        String username
) {
}
