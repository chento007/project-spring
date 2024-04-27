package co.istad.photostad.api.image.web;


public record ImageDto(
        Integer id,
        String uuid,
        String name,
        String url,
        String type

) {
}
