package co.istad.photostad.api.file.web;

import lombok.Builder;

@Builder
public record FileImageDto (
        String image,
        boolean status
){
}
