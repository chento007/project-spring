package co.istad.photostad.api.file.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record FileDto(
        String name,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String url,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String uuid,
        String extension,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String downloadUrl,
        long size
) { }
