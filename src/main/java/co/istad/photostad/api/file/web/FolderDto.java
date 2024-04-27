package co.istad.photostad.api.file.web;

import lombok.Builder;

import java.util.List;

@Builder
public record FolderDto(
        String folderName,
        List<String> listImage,
        List<String> url,
        Integer total,
        long size
) {
}
