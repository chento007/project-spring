package co.istad.photostad.api.file.web;


import co.istad.photostad.base.BaseRest;
import co.istad.photostad.api.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/upload-file-base64")
    public BaseRest<?> uploadFileBase64(@RequestBody String image) {

        FileDto fileDto = fileService.uploadFileBase64(image);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been upload success.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();

    }

    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart("file") MultipartFile file) {

        FileDto fileDto = fileService.uploadSingle(file);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been upload success.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();

    }

    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart("files") List<MultipartFile> files) {

        var result = fileService.uploadMultiple(files);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files have been upload success.")
                .timestamp(LocalDateTime.now())
                .data(result)
                .build();

    }

    @GetMapping
    public BaseRest<?> findAll() {

        List<FileDto> resultFiles = fileService.findAll();

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files have been found success.")
                .timestamp(LocalDateTime.now())
                .data(resultFiles)
                .build();

    }

    @GetMapping("/{name}")
    public BaseRest<?> findByName(@PathVariable("name") String name) {

        FileDto resultFiles = fileService.findByName(name);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File have been found success.")
                .timestamp(LocalDateTime.now())
                .data(resultFiles)
                .build();

    }

    @DeleteMapping("/{name}")
    public BaseRest<?> deleteByName(@PathVariable("name") String name) {

        String resultFiles = fileService.deleteByName(name);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been delete success.")
                .timestamp(LocalDateTime.now())
                .data(resultFiles)
                .build();

    }

    @DeleteMapping
    public BaseRest<?> removeAllFiles() {

        boolean resultFiles = fileService.removeAllFiles();

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("All File has been delete success.")
                .timestamp(LocalDateTime.now())
                .data(resultFiles)
                .build();

    }

    @GetMapping("/download/{name}")
    public ResponseEntity<?> downloadImage(@PathVariable("name") String name) {

        Resource resource = fileService.downloadImageFindByName(name);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment;filename=" + resource.getFilename())
                .body(resource);

    }

    @PostMapping("/upload-folder")
    public BaseRest<?> uploadFolder(@RequestPart("files") List<MultipartFile> imageList) {

        FolderDto resultFiles = fileService.uploadImageGenerateFolder(imageList);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Folder has been upload success.")
                .timestamp(LocalDateTime.now())
                .data(resultFiles)
                .build();

    }

    @PostMapping("/upload-font")
    public BaseRest<?> uploadFont(@RequestPart("file") MultipartFile file) {

        FileDto fileDto = fileService.uploadFont(file);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been upload success.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();

    }
}
