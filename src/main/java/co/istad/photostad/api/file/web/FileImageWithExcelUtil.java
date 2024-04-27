package co.istad.photostad.api.file.web;

import lombok.Builder;

@Builder
public record FileImageWithExcelUtil(
        String cellValue,
        Integer width,
        Integer height
) {
}
