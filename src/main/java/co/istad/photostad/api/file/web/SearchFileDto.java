package co.istad.photostad.api.file.web;

import lombok.Builder;

import java.io.File;
@Builder
public record SearchFileDto(
        File file,
        boolean status
) {
}
