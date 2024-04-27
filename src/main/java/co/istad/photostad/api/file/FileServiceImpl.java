package co.istad.photostad.api.file;

import co.istad.photostad.api.file.web.FileDto;
import co.istad.photostad.api.file.web.FileImageDto;
import co.istad.photostad.api.file.web.FolderDto;
import co.istad.photostad.config.ScheduledConfig;
import co.istad.photostad.util.FileUtil;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private FileUtil fileUtil;
    @Value("${file.server-path-image}")
    private String fileServerPathImage;
    @Value("${file.base-url}")
    private String baseUrl;
    @Value("${file.download-url}")
    private String fileDownloadUrl;
    @Autowired
    private ResourceLoader resourceLoader;
    private final ScheduledConfig scheduledConfig;
    private final ExecutorService executor = Executors.newFixedThreadPool(100);


    @Autowired
    public void setFileUtil(FileUtil fileUtil) {

        this.fileUtil = fileUtil;

    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {

        return fileUtil.upload(file);

    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {

        List<FileDto> fileDtoList = new ArrayList<>();

        for (MultipartFile file : files) {
            executor.submit(() -> fileDtoList.add(fileUtil.upload(file)));
        }

        return fileDtoList;

    }

    @Override
    public List<FileDto> findAll() {

        File file = new File(fileServerPathImage);

        if (Objects.requireNonNull(file.list()).length > 0) {

            List<File> fileList = new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
            List<FileDto> resultList = new ArrayList<>();

            for (File f : fileList) {
                resultList.add(
                        FileDto.builder()
                                .name(f.getName())
                                .url(baseUrl + f.getName())
                                .downloadUrl(fileDownloadUrl + f.getName())
                                .extension(fileUtil.getExtensionFile(f))
                                .size(f.length())
                                .build()
                );
            }

            return resultList;

        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No file in server."
        );

    }

    @Override
    public FileDto findByName(String fileName) {

        Resource resource = resourceLoader.getResource("file:" + fileServerPathImage + fileName);

        if (resource.exists()) {

            try {

                return FileDto.builder()
                        .name(resource.getFilename())
                        .url(baseUrl + resource.getFilename())
                        .downloadUrl(fileDownloadUrl + resource.getFilename())
                        .extension(fileUtil.getExtensionFile(resource.getFile()))
                        .size(resource.contentLength())
                        .build();

            } catch (IOException e) {

                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "The resource cannot be resolved as absolute file path."
                );

            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("File with name %s search not found.", fileName)
        );
    }

    @Override
    public String deleteByName(String filename) {

        Resource resource = resourceLoader.getResource("file:" + fileServerPathImage + filename);

        if (resource.exists()) {

            try {
                boolean delete = resource.getFile().delete();

                if (delete) {
                    return resource.getFilename();
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            String.format("Delete with %s is fail.", filename)
                    );
                }

            } catch (IOException e) {

                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "The resource cannot be resolved as absolute file path."
                );

            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("File with name %s search not found.", filename)
        );

    }

    @Override
    public boolean removeAllFiles() {


        try {
            Resource resource = resourceLoader.getResource("file:" + fileServerPathImage);
            File folder = ResourceUtils.getFile(resource.getURL());
            FileUtils.cleanDirectory(folder);
            return true;

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Delete all files fail ... ! because directory doesn't exist or resource not available."
            );

        }
    }

    @Override
    public FileDto uploadFileBase64(String image) {

        String[] record = image.split(",");
        String extension = fileUtil.getExtensionBase64(record[0]);
        String fileImage = record[1];
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(fileImage);
        String fileName = String.format("%s.%s", UUID.randomUUID(), extension);

        try {

            Path path = Paths.get(fileServerPathImage + fileName);
            Files.write(path, imageBytes);
            return FileDto.builder()
                    .name(fileName)
                    .url(baseUrl + fileName)
                    .downloadUrl(fileDownloadUrl + path.getFileName())
                    .extension(extension)
                    .build();

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload file failed , please try again."
            );

        }
    }

    @Override
    public Resource downloadImageFindByName(String name) {

        return fileUtil.findByName(name);

    }

//    @Override
//    public FolderDto uploadImageGenerateFolder(List<MultipartFile> imageList) {
//
//        String folderName = UUID.randomUUID().toString();
//        String downloadResourceFilename = fileServerPathImage + folderName;
//        Resource directory = fileUtil.createDirectory(downloadResourceFilename);
//        List<String> imageNameList = new ArrayList<>();
//
//        for (MultipartFile multipartFile : imageList) {
//            FileImageDto fileImage = fileUtil.uploadImageToFolder(multipartFile, downloadResourceFilename);
//            if (fileImage.status()) {
//                imageNameList.add(fileImage.image());
//            }
//        }
//
//        try {
//
//            return FolderDto.builder()
//                    .folderName(folderName)
//                    .total(imageNameList.size())
//                    .size(directory.contentLength())
//                    .listImage(imageNameList)
//                    .build();
//
//        } catch (IOException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Upload image is fail."
//            );
//        }
//    }


    @Override
    public FolderDto uploadImageGenerateFolder(List<MultipartFile> imageList) {
        String folderName = UUID.randomUUID().toString();
        String downloadResourceFilename = fileServerPathImage + folderName;
        Resource directory = fileUtil.createDirectory(downloadResourceFilename);
        List<String> imageNameList = new ArrayList<>();
        List<String> urlImageList=new ArrayList<>();
        CompletableFuture<Void>[] completableFutures = new CompletableFuture[imageList.size()];

        for (int i = 0; i < imageList.size(); i++) {
            MultipartFile multipartFile = imageList.get(i);
            if (fileUtil.isFileExe(multipartFile.getOriginalFilename())) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "You can not upload file exe."
                );
            }
            completableFutures[i] = CompletableFuture.runAsync(() -> {
                FileImageDto fileImage = fileUtil.uploadImageToFolder(multipartFile, downloadResourceFilename);
                if (fileImage.status()) {
                    synchronized (imageNameList) {
                        imageNameList.add(fileImage.image());
                    }
                }
            });
        }

        try {
            CompletableFuture.allOf(completableFutures).get(); // Wait for all tasks to complete
        } catch (InterruptedException | ExecutionException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload image failed."
            );
        }

        try {
            String baseUrlFolder=baseUrl+folderName+"/";
            urlImageList=imageNameList.stream().map(
                    s -> baseUrlFolder+s
            ).collect(Collectors.toList());
            return FolderDto.builder()
                    .folderName(folderName)
                    .total(imageNameList.size())
                    .size(directory.contentLength())
                    .url(urlImageList)
                    .listImage(imageNameList)
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload image failed."
            );
        }
    }

    @Override
    public FileDto uploadFont(MultipartFile font) {
        return fileUtil.uploadFont(font);
    }
}