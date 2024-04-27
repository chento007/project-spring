package co.istad.photostad.util;



import co.istad.photostad.api.file.web.FileDto;
import co.istad.photostad.api.file.web.FileImageDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
@Getter
public class FileUtil {

    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.base-url}")
    private String fileBaseUrl;
    @Value("${file.download-url}")
    private String fileDownloadUrl;
    @Value("${file.base-url}")
    private String baseUrl;
    @Value("${file.server-path-excel}")
    private String fileServerPathExcel;
    @Value("${file.server-path-image}")
    private String fileServerPathImage;
    @Value("${file.server-path-font}")
    private String fileServerFont;
    @Autowired
    private final HttpServletResponse response;
    @Autowired
    private ResourceLoader resourceLoader;

    public boolean deleteFile(String destination) {

        File directory = new File(destination);

        if (directory.exists()) {
            if (directory.isDirectory()) {
                Resource resource = resourceLoader.getResource("file:" + directory);
                try {
                    File folder = ResourceUtils.getFile(resource.getURL());
                    FileUtils.cleanDirectory(folder);
                    directory.delete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return true;
            } else {
                System.out.println("The given path is not a directory.");
            }
        } else {
            System.out.println("The directory does not exist.");
        }
        return false;
    }


    public Resource createDirectory(String locationName) {

        try {

            Path path = Paths.get(locationName);
            Resource directory = new UrlResource(path.toUri());

            if (!directory.exists()) {
                directory.getFile().mkdirs();
            }

            return directory;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeImageToFolder(BufferedImage image, String locationDownload) {

        try {

            ImageIO.write(image, "jpg", new File(locationDownload + "/" + UUID.randomUUID() + ".jpg"));

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Uploading watermark has been fail."
            );

        }
    }

    public boolean isFileExe(String filename) {

        int lastDotIndex = filename.lastIndexOf(".");
        String extension = filename.substring(lastDotIndex + 1);
        if (extension.equalsIgnoreCase("exe")) {
            return true;
        }
        return false;
    }

    public FileImageDto uploadImageToFolder(MultipartFile file, String folderName) {
        if (this.isFileExe(file.getOriginalFilename())) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can not upload file exe."
            );
        }
        int lastDotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);
        long size = file.getSize();
        String fileName = String.format("%s.%s", UUID.randomUUID(), extension);
        String url = String.format("%s%s", fileBaseUrl, fileName);
        Path path = Paths.get(folderName + "/" + fileName);
        try {

            Files.copy(file.getInputStream(), path);
            return FileImageDto.builder()
                    .status(true)
                    .image(fileName)
                    .build();

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload file failed , please try again."
            );
        }
    }


    public FileDto upload(MultipartFile file) {

        if (this.isFileExe(Objects.requireNonNull(file.getOriginalFilename()))) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can not upload file exe."
            );
        }

        int lastDotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);
        long size = file.getSize();
        String fileName = String.format("%s.%s", UUID.randomUUID(), extension);
        String url = String.format("%s%s", fileBaseUrl, fileName);
        Path path = Paths.get(fileServerPathImage + fileName);

        try {

            Files.copy(file.getInputStream(), path);

            return FileDto.builder()
                    .name(fileName)
                    .url(url)
                    .downloadUrl(fileDownloadUrl + path.getFileName())
                    .extension(extension)
                    .size(size)
                    .build();

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload file failed , please try again."
            );
        }
    }

    public FileDto uploadFont(MultipartFile file) {

        if (this.isFileExe(Objects.requireNonNull(file.getOriginalFilename()))) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can not upload file exe."
            );
        }
        int lastDotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);
        long size = file.getSize();
        String fileName = String.format("%s.%s", UUID.randomUUID(), extension);
        String url = String.format("%s%s", fileBaseUrl, fileName);
        Path path = Paths.get(fileServerFont + fileName);

        try {

            Files.copy(file.getInputStream(), path);

            return FileDto.builder()
                    .name(fileName)
                    .url(url)
                    .extension(extension)
                    .size(size)
                    .build();

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload file failed , please try again."
            );
        }
    }


    public String getExtensionBase64(String urlScheme) {

        String[] temp = urlScheme.split("/");
        String[] extension = temp[1].split(";");
        return extension[0];

    }

    public String getExtensionFile(File file) {

        int lastDotIndex = file.getName().lastIndexOf(".");
        return file.getName().substring(lastDotIndex + 1);

    }

    public String getExtensionStr(String imageName) {

        int lastDotIndex = imageName.lastIndexOf(".");
        return imageName.substring(lastDotIndex + 1);

    }

    public Resource findByNameGenerateCertificate(String filename) {


        try {
            Path path = Paths.get(fileServerPathImage + "templates/" + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("File with name %s search not found.", filename)
                );
            }

        } catch (MalformedURLException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "URL is not valid."
            );

        }
    }
    public Resource findByLogoCourseByName(String filename) {

        try {
            Path path = Paths.get(fileServerPathImage + "course_logo/" + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("File with name %s search not found.", filename)
                );
            }

        } catch (MalformedURLException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "URL is not valid."
            );

        }
    }
    public Resource findByName(String filename) {


        try {
            Path path = Paths.get(fileServerPathImage + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format("File with name %s search not found.", filename)
                );
            }

        } catch (MalformedURLException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "URL is not valid."
            );

        }
    }

    public List<String> findMultipleName(List<MultipartFile> ListFileName) {

        List<String> imagelist = new ArrayList<>();

        for (MultipartFile filename : ListFileName) {

            Path path = Paths.get(fileServerPathImage + filename.getOriginalFilename());

            try {

                Resource resource = new UrlResource(path.toUri());

                if (resource.exists()) {

                    imagelist.add(filename.getOriginalFilename());

                } else {

                    imagelist.add(fileServerPathImage + "no-photo.jpg");

                }
            } catch (MalformedURLException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "URL is not valid."
                );
            }
        }
        return imagelist;
    }


    public static void zipFolder(Path sourceFolderPath, Path zipPath) {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {

            Files.walk(sourceFolderPath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceFolderPath.relativize(path).toString());
                try {

                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();

                } catch (IOException e) {

                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Failed to create zip entry."
                    );

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
